package ignitis_homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserReportResponse {

    private UserResponse user;
    private Integer messagesCount;
    private String firstMessageDate;
    private String lastMessageDate;
    private Double averageMessageLength;
    private String lastMessageText;
}
