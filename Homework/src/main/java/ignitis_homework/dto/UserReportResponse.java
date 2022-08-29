package ignitis_homework.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserReportResponse {
    UserResponse user;
    Integer messagesCount;
    String firstMessageDate;
    String lastMessageDate;
    Integer averageMessageLength;
    String lastMessageText;
}
