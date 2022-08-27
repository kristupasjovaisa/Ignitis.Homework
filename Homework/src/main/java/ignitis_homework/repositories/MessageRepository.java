package ignitis_homework.repositories;

import ignitis_homework.entities.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Optional<Message> findFirstByOrderByCreatedAtDesc();
    Optional<Message> findFirstByOrderByCreatedAtAsc();
}