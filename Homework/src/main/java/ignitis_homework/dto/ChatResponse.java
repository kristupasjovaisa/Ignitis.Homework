package ignitis_homework.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ChatResponse {
    private Long id;
    private List<UserResponse> users;
}