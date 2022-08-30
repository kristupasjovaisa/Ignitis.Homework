package ignitis_homework.entities;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
}
