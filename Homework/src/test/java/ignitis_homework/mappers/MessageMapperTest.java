package ignitis_homework.mappers;

import ignitis_homework.dto.AddMessageRequest;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.Message;
import ignitis_homework.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {

    @Spy
    @InjectMocks
    MessageMapper messageMapper;

    @Test
    void mapToMessageResponse() {
        var message = Message.builder()
                .id(1l)
                .createdAt(Timestamp.valueOf("2022-01-01 00:00:00.123456789"))
                .text("text")
                .build();
        var actual = messageMapper.mapFrom(message);
        assertEquals(1, actual.getId());
        assertEquals("2022-01-01 00:00:00.123456789", actual.getCreatedAt());
        assertEquals("text", actual.getText());
    }

    @Test
    void mapToMessageEntity() {
        var request = AddMessageRequest.builder().text("request").build();
        var user = User.builder().id(1l).build();
        var chat = Chat.builder().id(1l).build();
        var actual = messageMapper.mapFrom(request, user, chat);
        assertEquals("request", actual.getText());
        assertEquals(1, actual.getUser().getId());
        assertEquals(1, actual.getChat().getId());
        assertNotNull(actual.getCreatedAt());
    }
}