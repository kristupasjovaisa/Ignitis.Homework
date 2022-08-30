package ignitis_homework.mappers;

import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ChatMapperTest {
    @Mock
    UserMapper userMapper;

    @Spy
    @InjectMocks
    ChatMapper chatMapper;

    @Test
    void mapFrom() {
        var user = User.builder().id(1l).build();
        var chat = Chat.builder().id(1l).users(List.of(user)).build();
        Mockito.when(userMapper.mapFrom(user)).thenReturn(UserResponse.builder().id(2l).build());
        var actual = chatMapper.mapFrom(chat);
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals(1, actual.getUsers().size());
        Assertions.assertEquals(2, actual.getUsers().get(0).getId());
    }

    @Test
    void testMapFrom() {
        var sender = User.builder().id(1l).build();
        var receiver = User.builder().id(2l).build();
        var actual = chatMapper.mapFrom(sender,receiver);
        Assertions.assertEquals(2,actual.getUsers().size());
        Assertions.assertEquals(1,actual.getUsers().get(0).getId());
        Assertions.assertEquals(2,actual.getUsers().get(1).getId());
    }
}