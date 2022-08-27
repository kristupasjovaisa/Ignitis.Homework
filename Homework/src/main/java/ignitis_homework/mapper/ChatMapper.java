package ignitis_homework.mapper;

import ignitis_homework.dto.AddChatRequest;
import ignitis_homework.dto.ChatResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMapper {

    public UserMapper userMapper;

    public ChatResponse mapfrom(Chat chat){
        return ChatResponse
                .builder()
                .id(chat.getId())
                .users(chat.getUsers()
                        .stream()
                        .map(user -> userMapper.mapFrom(user))
                        .collect(Collectors.toList()))
                .build();
    }

    public Chat mapFrom(User sender, User receiver){
        return Chat.builder()
                .users(List.of(sender, receiver))
                .build();
    }
}
