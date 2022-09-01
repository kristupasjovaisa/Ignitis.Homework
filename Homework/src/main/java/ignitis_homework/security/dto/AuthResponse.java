package ignitis_homework.security.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    String name;
    String email;
    String jwtToken;
    Long jwtTokenExpiresIn;
}
