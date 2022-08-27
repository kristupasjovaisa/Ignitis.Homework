package ignitis_homework.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddChatRequest {
    private Long userId;
}