package ignitis_homework.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Transactional
class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    void findFirstByOrderByCreatedAtDesc() {
        var actual = messageRepository.findFirstByOrderByCreatedAtDesc();
        Assertions.assertEquals(4, actual.get().getId());
    }

    @Test
    void findFirstByOrderByCreatedAtAsc() {
        var actual = messageRepository.findFirstByOrderByCreatedAtAsc();
        Assertions.assertEquals(1, actual.get().getId());
    }

    @Test
    void findAllByChatId() {
        var actual = messageRepository.findAllByChatId(1l).stream()
                .map(message -> message.getId())
                .collect(Collectors.toList());

        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.contains(1l));
        Assertions.assertTrue(actual.contains(2l));
    }
}