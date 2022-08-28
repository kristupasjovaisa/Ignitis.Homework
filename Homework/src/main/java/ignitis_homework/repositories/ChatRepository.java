package ignitis_homework.repositories;


import ignitis_homework.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select chat from Chat chat inner join chat.users user where user.id = :userId")
    List<Chat> findAllByUserId(Long userId);
}