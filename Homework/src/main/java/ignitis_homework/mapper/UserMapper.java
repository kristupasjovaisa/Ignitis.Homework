package ignitis_homework.mapper;

import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse mapFrom(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
