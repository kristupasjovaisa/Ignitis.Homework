package ignitis_homework.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserReportResponse {

    private UserResponse user;
    private Integer messagesCount;
    private String firstMessageDate;
    private String lastMessageDate;
    private Integer averageMessageLength;
    private String lastMessageText;
}
