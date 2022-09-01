package ignitis_homework.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Message> createdMessages;

    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Chat> chats;

    @ManyToMany
    private Set<Authority> authorities;
}
