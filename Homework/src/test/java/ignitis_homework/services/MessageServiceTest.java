package ignitis_homework.services;

import ignitis_homework.dto.AddMessageRequest;
import ignitis_homework.dto.MessageResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.Message;
import ignitis_homework.entities.User;
import ignitis_homework.mappers.MessageMapper;
import ignitis_homework.repositories.ChatRepository;
import ignitis_homework.repositories.MessageRepository;
import ignitis_homework.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @InjectMocks
    MessageService messageService;
    @Mock
    MessageRepository messageRepository;
    @Mock
    ChatRepository chatRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    MessageMapper mapper;

    @Test
    void getAllMessages() {
        User user = User.builder().id(1l).build();
        var chat = Chat.builder().id(2l).users(List.of(user)).build();
        Mockito.when(chatRepository.findById(1l)).thenReturn(Optional.of(chat));
        var message = Message.builder().id(1l).build();
        var messages = List.of(message);
        Mockito.when(messageRepository.findAllByChatId(2l)).thenReturn(messages);
        Mockito.when(mapper.mapFrom(message)).thenReturn(MessageResponse.builder().id(2l).build());

        var actual = messageService.getAllMessages(1l, 1l);
        Mockito.verify(mapper, Mockito.times(1)).mapFrom(message);
        assertEquals(1, actual.size());
        assertEquals(2l, actual.get(0).getId());
    }

    @Test
    void addMessage() {
        var userEntity = User.builder().id(2l).build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(userEntity));
        var chat = Chat.builder().id(2l).users(List.of(User.builder().id(1l).build())).build();
        Mockito.when(chatRepository.findById(1l)).thenReturn(Optional.of(chat));

        var message = Message.builder().id(1l).build();
        var request = AddMessageRequest.builder().text("text").build();
        Mockito.when(mapper.mapFrom(request, userEntity, chat))
                .thenReturn(message);
        var createdMessage = Message.builder().id(2l).build();
        Mockito.when(messageRepository.save(message)).thenReturn(createdMessage);
        Mockito.when(mapper.mapFrom(createdMessage)).thenReturn(MessageResponse.builder().id(3l).build());

        var actual = messageService.addMessage(request, 1l, 1l);
        assertEquals(3l, actual.get().getId());
    }
}