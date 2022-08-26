package ignitis_homework.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
}
