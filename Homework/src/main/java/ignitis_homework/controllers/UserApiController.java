package ignitis_homework.controllers;

import ignitis_homework.common.OpenApi;
import ignitis_homework.dto.*;
import ignitis_homework.security.dto.AddUserRequest;
import ignitis_homework.services.ChatService;
import ignitis_homework.services.MessageService;
import ignitis_homework.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserApiController.USERS_ROOT_PATH)
@RequiredArgsConstructor
@OpenApi
@ApiResponses(value = @ApiResponse(code = 401, message = "User must be authorized"))
@Api(tags = "Users Controller")
public class UserApiController {

    private final UserService userService;
    private final MessageService messageService;
    private final ChatService chatService;
    public static final String USERS_ROOT_PATH = "/users";
    public static final String USERS_USERID_PATH = "/{userId}";
    public static final String USERS_USERID_CHATS_PATH = USERS_USERID_PATH + "/chats";
    public static final String USERS_USERID_CHATS_CHATID_MESSAGES_PATH = USERS_USERID_CHATS_PATH + "/{chatId}/messages";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all the users", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users returned successfully")
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(path = USERS_USERID_PATH)
    @ApiOperation(value = "Get a user by id", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User returned successfully")
    })
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Long id) {
        var user = userService.getUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiOperation(value = "Add a user", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User added successfully"),
            @ApiResponse(code = 403, message = "User is not granted to add a user")
    })
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<UserResponse> addUser(@RequestBody AddUserRequest request) {
        return new ResponseEntity<>(userService.addUser(request), HttpStatus.CREATED);
    }

    @DeleteMapping(path = USERS_USERID_PATH)
    @ApiOperation(value = "Delete user", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted successfully"),
            @ApiResponse(code = 403, message = "User is not granted to delete a user")
    })
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = USERS_USERID_CHATS_PATH)
    @ApiOperation(value = "Get all the chats of the user", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chats returned successfully")
    })
    public ResponseEntity<List<ChatResponse>> getChats(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(chatService.getChats(userId));
    }

    @PostMapping(path = USERS_USERID_CHATS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Create chat",
            notes = "If chat already exists with the sender and receiver a new chat is not created, but returned the existing one." +
                    "Otherwise a new chat is created and returned.",
            httpMethod = "POST"
    )
    @ApiResponses(value = @ApiResponse(code = 201, message = "Chat created successfully"))
    public ResponseEntity<ChatResponse> addChat(@PathVariable("userId") Long userId, @RequestBody AddChatRequest addChat) {
        var chat = chatService.addChat(addChat, userId);
        if (chat.isPresent()) {
            return new ResponseEntity<>(chat.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = USERS_USERID_CHATS_CHATID_MESSAGES_PATH)
    @ApiOperation(value = "Get all the messages of the chat", httpMethod = "GET")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Messages returned successfully"))
    public ResponseEntity<List<MessageResponse>> getAllMessages(@PathVariable("userId") Long userId, @PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(messageService.getAllMessages(userId, chatId));
    }

    @PostMapping(path = USERS_USERID_CHATS_CHATID_MESSAGES_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a message", httpMethod = "POST")
    @ApiResponses(value = @ApiResponse(code = 201, message = "Message created successfully"))
    public ResponseEntity<MessageResponse> addMessage(@PathVariable("userId") Long senderId, @PathVariable("chatId") Long chatId, @RequestBody AddMessageRequest addMessage) {
        var message = messageService.addMessage(addMessage, senderId, chatId);
        if (message.isPresent()) {
            return new ResponseEntity<>(message.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }
}
