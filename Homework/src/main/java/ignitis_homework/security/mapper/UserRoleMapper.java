package ignitis_homework.security.mapper;

import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.Authority;
import ignitis_homework.entities.User;
import ignitis_homework.security.dto.AuthResponse;
import ignitis_homework.security.dto.UserRoleDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserRoleMapper {

    public UserRoleDto mapUserRoleFrom(User user) {
        return UserRoleDto.builder()
                .user(user)
                .roles(user.getAuthorities().stream()
                        .map(getAuthority())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                )
                .build();
    }

    private Function<Authority, String> getAuthority() {
        return authority -> "ROLE_" + authority.getRole();
    }
}
