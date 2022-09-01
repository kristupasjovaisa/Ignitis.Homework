package ignitis_homework.security.controller;

import ignitis_homework.common.OpenApi;
import ignitis_homework.security.dto.LoginRequest;
import ignitis_homework.security.dto.AuthResponse;
import ignitis_homework.security.dto.UserRoleDto;
import ignitis_homework.security.mapper.AuthResponseMapper;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Api(tags = "Authentication Controller")
@OpenApi
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final AuthResponseMapper authResponseMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) throws Exception {
        return Optional.of(authenticate(request.getEmail(), request.getPassword()))
                .map(auth -> (UserRoleDto) auth.getPrincipal())
                .map(authResponseMapper::mapFrom)
                .map(authResponse -> ok(authResponse))
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
