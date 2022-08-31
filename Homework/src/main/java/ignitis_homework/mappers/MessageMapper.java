package ignitis_homework.mappers;

import ignitis_homework.dto.AddMessageRequest;
import ignitis_homework.dto.MessageResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.Message;
import ignitis_homework.entities.User;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MessageMapper {

    public MessageResponse mapFrom(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .createdAt(String.valueOf(message.getCreatedAt()))
                .text(message.getText())
                .build();
    }

    public Message mapFrom(AddMessageRequest message, User sender, User receiver, Chat chat) {
        return Message.builder()
                .chat(chat)
                .owner(sender)
                .receiver(receiver)
                .text(message.getText())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
