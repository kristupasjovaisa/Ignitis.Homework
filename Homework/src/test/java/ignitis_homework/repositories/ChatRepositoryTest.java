package ignitis_homework.repositories;

import ignitis_homework.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ChatRepositoryTest {

    @Autowired
    ChatRepository chatRepository;

    @Test
    void findByUserIds() {
        assertTrue(chatRepository.findByUserId(3l).isEmpty());
        assertEquals(1l, chatRepository.findByUserId(1l).get().getId());
    }
}