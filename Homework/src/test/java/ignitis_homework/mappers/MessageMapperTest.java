package ignitis_homework.mappers;

import ignitis_homework.dto.AddMessageRequest;
import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.Message;
import ignitis_homework.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {
    @Mock
    UserMapper userMapper;
    @Spy
    @InjectMocks
    MessageMapper messageMapper;

    @Test
    void mapToMessageResponse() {
        var user = User.builder().build();
        var message = Message.builder()
                .id(1l)
                .createdAt(Timestamp.valueOf("2022-01-01 00:00:00.123456789"))
                .owner(user)
                .text("text")
                .build();
        when(userMapper.mapFrom(user)).thenReturn(UserResponse.builder().id(1l).build());
        var actual = messageMapper.mapFrom(message);
        assertEquals(1, actual.getId());
        assertEquals(1, actual.getSender().getId());
        assertEquals("2022-01-01 00:00:00.123456789", actual.getCreatedAt());
        assertEquals("text", actual.getText());
    }

    @Test
    void mapToMessageEntity() {
        var request = AddMessageRequest.builder().text("request").build();
        var sender = User.builder().id(1l).build();
        var receiver = User.builder().id(2l).build();
        var chat = Chat.builder().id(1l).build();

        var actual = messageMapper.mapFrom(request, sender, receiver, chat);
        assertEquals("request", actual.getText());
        assertEquals(1, actual.getOwner().getId());
        assertEquals(2, actual.getReceiver().getId());
        assertEquals(1, actual.getChat().getId());
        assertNotNull(actual.getCreatedAt());
    }
}