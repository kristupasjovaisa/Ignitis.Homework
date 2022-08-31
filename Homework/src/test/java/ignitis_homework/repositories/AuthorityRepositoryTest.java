package ignitis_homework.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional
class AuthorityRepositoryTest {

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    void findAllByRoles() {
        var actual = authorityRepository.findAllByRoles(List.of("Admin", "Manager")).stream()
                .map(authority -> authority.getId())
                .collect(Collectors.toList());
        assertEquals(2, actual.size());
        assertTrue(actual.contains(1l));
        assertTrue(actual.contains(2l));
    }
}