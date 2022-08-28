package ignitis_homework.services;

import ignitis_homework.dto.AddChatRequest;
import ignitis_homework.dto.ChatResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.mapper.ChatMapper;
import ignitis_homework.repositories.ChatRepository;
import ignitis_homework.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper mapper;

    public Optional<Chat> addChat(AddChatRequest addChat, Long senderId) {
        var sender = userRepository.findById(senderId);
        var receiver = userRepository.findById(addChat.getReceiver());
        if (sender.isPresent() && receiver.isPresent()) {
            var commonChat = chatRepository
                    .findAllByUserId(senderId).stream()
                    .filter(chat ->
                            chat.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList())
                                    .contains(receiver.get().getId())
                    )
                    .findFirst();
            if (commonChat.isPresent()) {
                return commonChat;
            }
            return Optional.of(chatRepository.save(mapper.mapFrom(sender.get(), receiver.get())));
        }
        return Optional.empty();
    }

    public List<ChatResponse> getChats(Long userId) {
        return chatRepository.findAllByUserId(userId).stream().map(mapper::mapfrom).collect(Collectors.toList());
    }
}
