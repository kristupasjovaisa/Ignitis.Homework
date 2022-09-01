package ignitis_homework.repositories;

import ignitis_homework.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    @Query("SELECT a FROM Authority a WHERE a.role in :roles")
    Set<Authority> findAllByRoles(List<String> roles);
}
