package ignitis_homework.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatResponse {
    private Long id;
    private UserResponse users;
}
