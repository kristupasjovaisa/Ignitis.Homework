package ignitis_homework.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class MessageResponse {
    private Long id;
    private String text;
    private Timestamp createdAt;
}
