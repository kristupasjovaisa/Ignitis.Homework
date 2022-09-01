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

    public Optional<MessageResponse> addMessage(AddMessageRequest message, Long senderId, Long chatId) {
        var sender = userRepository.findById(senderId);
        var receiver = userRepository.findById(message.getReceiverId());
        var chat = chatRepository.findById(chatId);
        if (sender.isPresent() && receiver.isPresent() && chat.isPresent()) {
            if (isChatContainingUser(chat.get(), sender.get().getId(), receiver.get().getId())) {
                var createdChat = messageRepository.save(mapper.mapFrom(message, sender.get(), receiver.get(), chat.get()));
                return Optional.of(mapper.mapFrom(createdChat));
            }
        }
        return Optional.empty();
    }

    private boolean isChatContainingUser(Chat chat, Long senderId, Long receiverId) {
        var userIds = getUserIds(chat);
        return userIds.contains(senderId) && userIds.contains(receiverId);
    }

    private boolean isChatContainingUser(Chat chat, Long userId) {
        return getUserIds(chat).contains(userId);
    }

    public List<Long> getUserIds(Chat chat) {
        return chat.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList());
    }
}