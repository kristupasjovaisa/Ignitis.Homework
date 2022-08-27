package ignitis_homework.repositories;


import ignitis_homework.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("select chat from Chat chat inner join chat.users user where user.id = :userId")
    Optional<Chat> findByUserId(Long userId);
    @Query("select chat from Chat chat inner join chat.users user where user.id = :userId")
    List<Chat> findAllByUserId(Long userId);
}