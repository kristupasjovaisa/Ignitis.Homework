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
            var senderChat = chatRepository.findByUserId(senderId);
            var receiverChat = chatRepository.findByUserId(receiver.get().getId());
            if (senderChat.isPresent() && receiverChat.isPresent() && senderChat.get().getId().equals(receiverChat.get().getId())) {
                return senderChat;
            }
            return Optional.of(chatRepository.save(mapper.mapFrom(sender.get(), receiver.get())));
        }
        return Optional.empty();
    }

    public List<ChatResponse> getChats(Long userId) {
        return chatRepository.findAllByUserId(userId).stream().map(mapper::mapfrom).collect(Collectors.toList());
    }
}
