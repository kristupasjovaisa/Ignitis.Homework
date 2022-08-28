package ignitis_homework.mapper;

import ignitis_homework.dto.AddMessageRequest;
import ignitis_homework.dto.MessageResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.Message;
import ignitis_homework.entities.User;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class MessageMapper {

    public MessageResponse mapFrom(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .createdAt(message.getCreatedAt())
                .text(message.getText())
                .build();
    }

    public Message mapFrom(AddMessageRequest message, User user, Chat chat) {
        return Message.builder()
                .chat(chat)
                .user(user)
                .text(message.getText())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
