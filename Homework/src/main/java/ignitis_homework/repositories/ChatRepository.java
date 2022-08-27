package ignitis_homework.repositories;


import ignitis_homework.entities.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat,Long> {
}