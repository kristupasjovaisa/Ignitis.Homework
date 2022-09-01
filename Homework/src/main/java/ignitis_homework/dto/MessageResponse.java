package ignitis_homework.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageResponse {
    private Long id;
    private UserResponse sender;
    private String text;
    private String createdAt;
}
