package ignitis_homework.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Message> messages;

    @ManyToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Chat> chats;
}
