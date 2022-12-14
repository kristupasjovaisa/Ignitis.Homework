package ignitis_homework.mappers;

import ignitis_homework.dto.ChatResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final UserMapper userMapper;

    public ChatResponse mapFrom(Chat chat) {
        return ChatResponse
                .builder()
                .id(chat.getId())
                .users(chat.getUsers()
                        .stream()
                        .map(user -> userMapper.mapFrom(user))
                        .collect(Collectors.toList()))
                .build();
    }

    public Chat mapFrom(User sender, User receiver) {
        return Chat.builder()
                .users(List.of(sender, receiver))
                .build();
    }
}
