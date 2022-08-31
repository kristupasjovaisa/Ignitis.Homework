package ignitis_homework.security.controller;

import ignitis_homework.common.OpenApi;
import ignitis_homework.security.dto.LoginRequest;
import ignitis_homework.security.dto.LoginResponse;
import ignitis_homework.security.dto.UserRoleDto;
import ignitis_homework.security.service.JwtProvider;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/login")
@Api(tags = "Login Controller")
@OpenApi
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest authRequest) throws Exception {
        return Optional.of(authenticate(authRequest.getEmail(), authRequest.getPassword()))
                .map(auth -> (UserRoleDto) auth.getPrincipal())
                .map(principal -> ok(LoginResponse.of(
                        principal.getUserFullName(),
                        authRequest.getEmail(),
                        jwtProvider.getToken(principal),
                        jwtProvider.getExpiresInSeconds()
                )))
                .orElseThrow(() -> new BadCredentialsException("Authentication failed"));
    }

    private Authentication authenticate(String email, String password) throws Exception {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
