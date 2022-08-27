package ignitis_homework.mapper;

import ignitis_homework.dto.AddChatRequest;
import ignitis_homework.dto.ChatResponse;
import ignitis_homework.entities.Chat;
import org.springframework.stereotype.Component;

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
                        .map(userMapper::mapFrom)
                        .collect(Collectors.toList()))
                .build();
    }

    public Chat mapFrom(AddChatRequest chat){
        return Chat.builder()
                .id(chat.getUserId())
                .build();
    }
}
