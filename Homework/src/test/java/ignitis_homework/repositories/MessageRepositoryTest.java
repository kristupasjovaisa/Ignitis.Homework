package ignitis_homework.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void findFirstByOrderByCreatedAtDesc() {
        var actual = messageRepository.findFirstByOrderByCreatedAtDesc();
        assertThat(actual.get().getId()).isEqualTo(2l);
    }

    @Test
    void findFirstByOrderByCreatedAtAsc() {
        var actual = messageRepository.findFirstByOrderByCreatedAtAsc();
        assertThat(actual.get().getId()).isEqualTo(1l);
    }
}