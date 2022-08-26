package ignitis_homework.repositories;

import ignitis_homework.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findFirstByOrderByCreatedAtDesc();
    Optional<Message> findFirstByOrderByCreatedAtAsc();
}