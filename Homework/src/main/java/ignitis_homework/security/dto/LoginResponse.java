package ignitis_homework.security.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class LoginResponse {
    String name;
    String email;
    String jwtToken;
    Long jwtTokenExpiresIn;
}
