package ignitis_homework.repositories;


import ignitis_homework.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select chat from Chat chat inner join chat.users user where user.id = :userId")
    List<Chat> findAllByUserId(Long userId);

    @Query(value = "SELECT CHATS.*" +
            "        FROM CHATS" +
            "        WHERE EXISTS (SELECT 1 FROM CHATS_USERS" +
            "        INNER JOIN USERS ON USERS.ID = CHATS_USERS.USERS_ID" +
            "        WHERE CHATS_USERS.CHATS_ID = CHATS.ID" +
            "        AND USERS.ID = :senderId)" +
            "        AND   EXISTS (SELECT 1 FROM CHATS_USERS" +
            "        INNER JOIN USERS ON USERS.ID = CHATS_USERS.USERS_ID" +
            "        WHERE CHATS_USERS.CHATS_ID = CHATS.ID" +
            "        AND USERS.ID = :receiverId)", nativeQuery = true)
    Optional<Chat> findByUserIds(Long senderId, Long receiverId);
}