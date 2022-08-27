package ignitis_homework.repositories;

import ignitis_homework.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
