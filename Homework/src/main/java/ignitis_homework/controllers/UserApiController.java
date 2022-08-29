package ignitis_homework.controllers;

import ignitis_homework.dto.*;
import ignitis_homework.services.ChatService;
import ignitis_homework.services.MessageService;
import ignitis_homework.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(UserApiController.USER_ROOT_PATH)
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final MessageService messageService;
    private final ChatService chatService;
    public static final String USER_ROOT_PATH = "/users";
    public static final String USER_USERID_PATH = "/{userId}";
    public static final String CHAT_ROOT_PATH = USER_USERID_PATH + "/chats";
    public static final String CHAT_CHATID_PATH = "/{chatId}";
    public static final String MESSAGE_ROOT_PATH = CHAT_ROOT_PATH + CHAT_CHATID_PATH + "/messages";
    public static final String REPORTS_PATH = "/reports";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(path = REPORTS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserReportResponse>> getUserReports() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(path = USER_USERID_PATH)
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Long id) {
        var user = userService.getUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = CHAT_ROOT_PATH)
    public ResponseEntity<List<ChatResponse>> getChats(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(chatService.getChats(userId));
    }

    @PostMapping(path = CHAT_ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatResponse> addChat(@PathVariable("userId") Long userId, @RequestBody AddChatRequest addChat) {
        var chat = chatService.addChat(addChat, userId);
        if (chat.isPresent()) {
            return new ResponseEntity<>(chat.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = MESSAGE_ROOT_PATH)
    public ResponseEntity<List<MessageResponse>> getAllMessages(@PathVariable("userId") Long userId, @PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(messageService.getAllMessages(userId, chatId));
    }

    @PostMapping(path = MESSAGE_ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> addMessage(@PathVariable("userId") Long userId, @PathVariable("chatId") Long chatId, @RequestBody AddMessageRequest addMessage) {
        var message = messageService.addMessage(addMessage, userId, chatId);
        if (message.isPresent()) {
            return new ResponseEntity<>(message.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }
}
