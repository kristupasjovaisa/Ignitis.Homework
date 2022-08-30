package ignitis_homework.mappers;

import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.Message;
import ignitis_homework.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    @Spy
    @InjectMocks
    UserMapper userMapper;

    @Test
    void mapFrom() {
        var user = User.builder()
                .id(1l)
                .name("name")
                .email("email")
                .build();
        var actual = userMapper.mapFrom(user);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("name", actual.getName());
        Assertions.assertEquals("email", actual.getEmail());
    }

    @Test
    void mapToReportFrom() {
        var message1 = Message.builder()
                .id(1l)
                .createdAt(Timestamp.valueOf("2022-01-01 00:00:00.123456789"))
                .text("12345678")
                .build();
        var message2 = Message.builder()
                .id(2l)
                .createdAt(Timestamp.valueOf("2022-01-02 00:00:00.123456789"))
                .text("123456789")
                .build();

        var user = User.builder().messages(List.of(message1, message2)).build();
        Mockito.when(userMapper.mapFrom(user)).thenReturn(UserResponse.builder().id(1l).build());
        var actual = userMapper.mapToReportFrom(user);
        Assertions.assertEquals(1l, actual.getUser().getId());
        Assertions.assertEquals(2, actual.getMessagesCount());
        Assertions.assertEquals("2022-01-01 00:00:00.123456789", actual.getFirstMessageDate());
        Assertions.assertEquals("2022-01-02 00:00:00.123456789", actual.getLastMessageDate());
        Assertions.assertEquals(8.5, actual.getAverageMessageLength());
        Assertions.assertEquals("123456789", actual.getLastMessageText());
    }
}