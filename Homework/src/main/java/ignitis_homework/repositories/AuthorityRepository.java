package ignitis_homework.repositories;

import ignitis_homework.entities.Authority;
import ignitis_homework.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.authorities WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);
}
