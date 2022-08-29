package ignitis_homework.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ChatRepositoryTest {
    @Autowired
    ChatRepository chatRepository;

    @Test
    void findAllByUserId() {
        var chats = chatRepository.findAllByUserId(1l)
                .stream()
                .map(chat -> chat.getId())
                .collect(Collectors.toList());
        assertEquals(2l, chats.size());
        assertTrue(chats.contains(1l));
        assertTrue(chats.contains(2l));
    }

    @Test
    void findByUserIdsReturnsChat() {
        var chat = chatRepository.findByUserIds(1l, 2l);
        assertEquals(1l, chat.get().getId());
    }

    @Test
    void findByUserIdsEmpty() {
        var chat = chatRepository.findByUserIds(1l, 4l);
        assertTrue(chat.isEmpty());
    }
}