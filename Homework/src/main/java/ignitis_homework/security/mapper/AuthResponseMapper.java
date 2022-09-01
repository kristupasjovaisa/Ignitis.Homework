package ignitis_homework.security.mapper;

import ignitis_homework.security.dto.AuthResponse;
import ignitis_homework.security.dto.UserRoleDto;
import ignitis_homework.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthResponseMapper {
    private final JwtProvider jwtProvider;

    public AuthResponse mapFrom(UserRoleDto dto) {
        return AuthResponse.builder()
                .name(dto.getUserFullName())
                .email(dto.getUsername())
                .jwtToken(jwtProvider.getToken(dto))
                .jwtTokenExpiresIn(jwtProvider.getExpiresInSeconds())
                .build();
    }
}
