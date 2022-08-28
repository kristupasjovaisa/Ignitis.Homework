package ignitis_homework.services;

import ignitis_homework.dto.AddMessageRequest;
import ignitis_homework.dto.MessageResponse;
import ignitis_homework.entities.Chat;
import ignitis_homework.mappers.MessageMapper;
import ignitis_homework.repositories.ChatRepository;
import ignitis_homework.repositories.MessageRepository;
import ignitis_homework.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageMapper mapper;

    public List<MessageResponse> getAllMessages(Long userId, Long chatId) {
        var chat = chatRepository.findById(chatId);
        if (chat.isPresent()) {
            if (isChatContainingUser(chat.get(), userId)) {
                return messageRepository.findAllByChatId(chat.get().getId()).stream().map(mapper::mapFrom).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    public Optional<MessageResponse> addMessage(AddMessageRequest message, Long userId, Long chatId) {
        var user = userRepository.findById(userId);
        var chat = chatRepository.findById(chatId);
        if (user.isPresent() && chat.isPresent()) {
            if (isChatContainingUser(chat.get(), userId)) {
                var createdChat = messageRepository.save(mapper.mapFrom(message, user.get(), chat.get()));
                return Optional.of(mapper.mapFrom(createdChat));
            }
        }
        return Optional.empty();
    }

    private boolean isChatContainingUser(Chat chat, Long userId) {
        var userIds = chat.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList());
        return userIds.contains(userId);
    }
}