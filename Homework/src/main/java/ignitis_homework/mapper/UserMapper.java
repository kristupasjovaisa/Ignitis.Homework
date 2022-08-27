package ignitis_homework.mapper;

import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse mapFrom(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .chatIds(user.getChats().stream().map(chat -> chat.getId()).collect(Collectors.toList()))
                .build();
    }
}
