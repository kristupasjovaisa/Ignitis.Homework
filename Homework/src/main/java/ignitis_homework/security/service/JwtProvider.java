package ignitis_homework.security.service;

import ignitis_homework.repositories.UserRepository;
import ignitis_homework.security.dto.UserRoleDto;
import ignitis_homework.security.mapper.UserRoleMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserRepository userRepository;
    private final UserRoleMapper userRoleMapper;

    @Value("#{${security.jwt.validity-time} * 60 * 1000}")
    private long tokenValidityInMillis;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.secretKeyFor(signatureAlgorithm);
    }

    public String getToken(UserRoleDto principal) {
        final Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("Iginitis_Homework-api")
                .setAudience("Iginitis_Homework-ui")
                .setSubject(principal.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidityInMillis))
                .claim("roles", principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .signWith(secretKey)
                .compact();
    }

    public Authentication parseToken(String token) {
        // validate token by secret key and get JWT payload as Claims
        Claims parsedJwtBody = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();


        String username = parsedJwtBody.getSubject();
        List<GrantedAuthority> authorities = ((List<String>) parsedJwtBody.get("roles")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var entity = userRepository.findUserByEmailWithAuthorities(username);
        if (entity.isPresent()) {
            return new UsernamePasswordAuthenticationToken(userRoleMapper.mapUserRoleFrom(entity.get()), null, authorities);
        }
        return new UsernamePasswordAuthenticationToken(null, null, authorities);
    }

    public Long getExpiresInSeconds() {
        return tokenValidityInMillis / 1000;
    }
}
