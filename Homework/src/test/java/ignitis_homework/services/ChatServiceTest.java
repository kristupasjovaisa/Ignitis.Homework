package ignitis_homework.services;

import ignitis_homework.dto.AddChatRequest;
import ignitis_homework.dto.ChatResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.entities.User;
import ignitis_homework.mapper.ChatMapper;
import ignitis_homework.repositories.ChatRepository;
import ignitis_homework.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @InjectMocks
    ChatService chatService;

    @Mock
    ChatRepository chatRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ChatMapper mapper;

    @Test
    void chatNotSavedButReturned() {
        User sender = User.builder().id(10l).build();
        User receiver = User.builder().id(20l).build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(sender));
        Mockito.when(userRepository.findById(2l)).thenReturn(Optional.of(receiver));

        Chat chat = Chat.builder().id(1l).build();
        Mockito.when(chatRepository.findByUserIds(10l, 20l)).thenReturn(Optional.of(chat));
        var request = AddChatRequest.builder().receiver(2l).build();
        var actual = chatService.addChat(request, 1l);

        Mockito.verify(chatRepository, Mockito.times(0)).save(any());
        Assertions.assertEquals(1l, actual.get().getId());
    }

    @Test
    void chatSaved() {
        User sender = User.builder().id(10l).build();
        User receiver = User.builder().id(20l).build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(sender));
        Mockito.when(userRepository.findById(2l)).thenReturn(Optional.of(receiver));

        Mockito.when(chatRepository.findByUserIds(10l, 20l)).thenReturn(Optional.empty());
        var chat = Chat.builder().id(1l).build();
        Mockito.when(mapper.mapFrom(sender, receiver)).thenReturn(chat);

        Mockito.when(chatRepository.save(chat)).thenReturn(Chat.builder().id(2l).build());

        var request = AddChatRequest.builder().receiver(2l).build();
        var actual = chatService.addChat(request, 1l);
        Assertions.assertEquals(2l, actual.get().getId());
    }

    @Test
    void getChats() {
        List<Chat> chats = new ArrayList<>();
        Chat chat = Chat.builder().id(1l).build();
        chats.add(chat);
        Mockito.when(chatRepository.findAllByUserId(1l)).thenReturn(chats);

        ChatResponse response = ChatResponse.builder().id(2l).build();
        Mockito.when(mapper.mapfrom(chat)).thenReturn(response);

        var actual = chatService.getChats(1l);
        Mockito.verify(mapper, Mockito.times(1)).mapfrom(chat);

        Assertions.assertEquals(1, actual.size());
    }
}