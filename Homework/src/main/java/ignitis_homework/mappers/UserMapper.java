package ignitis_homework.mappers;

import ignitis_homework.dto.UserReportResponse;
import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.Authority;
import ignitis_homework.entities.Message;
import ignitis_homework.entities.User;
import ignitis_homework.security.dto.AddUserRequest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public User mapFrom(AddUserRequest request, Set<Authority> authority) {
        return User.builder().
                name(request.getName()).
                email(request.getEmail()).
                password(passwordEncoder.encode(request.getPassword())).
                authorities(authority).
                build();
    }

    public UserResponse mapFrom(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserReportResponse mapToReportFrom(User user) {
        var userResponse = mapFrom(user);
        var messages = user.getCreatedMessages().stream().sorted(Comparator.comparing(Message::getCreatedAt)).collect(Collectors.toList());
        var lastMessage = messages.stream().reduce((first, second) -> second);
        var firstMessage = messages.stream().findFirst();
        var messagesCount = messages.size();
        var firstMessageDate = firstMessage.map(message -> message.getCreatedAt().toString()).orElse(null);
        var lastMessageDate = lastMessage.map(message -> message.getCreatedAt().toString()).orElse(null);
        var averageMessageLength = messages.stream()
                .map(message -> message.getText().chars().count())
                .mapToLong(l -> l).average().orElse(0);
        var lastMessageText = lastMessage.map(message -> message.getText()).orElse(null);
        return UserReportResponse.builder()
                .user(userResponse)
                .messagesCount(messagesCount)
                .firstMessageDate(firstMessageDate)
                .lastMessageDate(lastMessageDate)
                .averageMessageLength(averageMessageLength)
                .lastMessageText(lastMessageText)
                .build();
    }
}
