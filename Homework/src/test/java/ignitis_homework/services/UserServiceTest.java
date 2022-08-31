package ignitis_homework.services;

import ignitis_homework.dto.UserReportResponse;
import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.Authority;
import ignitis_homework.entities.User;
import ignitis_homework.mappers.UserMapper;
import ignitis_homework.repositories.AuthorityRepository;
import ignitis_homework.repositories.UserRepository;
import ignitis_homework.security.dto.AddUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper mapper;
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Test
    void addUser() {
        var request = AddUserRequest.builder().roles(List.of("Admin")).build();
        var authority = Authority.builder().role("Manager").build();
        var user = User.builder().id(1l).build();
        Mockito.when(authorityRepository.findAllByRoles(request.getRoles())).thenReturn(Set.of(authority));
        Mockito.when(mapper.mapFrom(request, Set.of(authority))).thenReturn(user);

        Mockito.when(mapper.mapFrom(userRepository.save(user))).thenReturn(UserResponse.builder().id(2l).build());
        var actual = userService.addUser(request);
        assertEquals(2, actual.getId());
    }

    @Test
    void deleteUser() {
        User user = User.builder()
                .id(2l)
                .build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        boolean actual = userService.deleteUser(1l);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(2l);
        assertTrue(actual);
    }

    @Test
    void getUser() {
        User user = User.builder()
                .id(1l)
                .build();
        UserResponse userDto = UserResponse.builder()
                .id(1l)
                .build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Mockito.when(mapper.mapFrom(user)).thenReturn(userDto);
        Optional<UserResponse> actual = userService.getUser(1l);
        assertEquals(1l, actual.get().getId());
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        User user = User.builder().id(1l).build();
        users.add(user);

        UserResponse userResponses = UserResponse.builder().id(2l).build();

        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(mapper.mapFrom(user)).thenReturn(userResponses);
        List<UserResponse> actual = userService.getAllUsers();
        assertEquals(2l, actual.get(0).getId());
        assertEquals(1, actual.size());
    }

    @Test
    void getUserReports() {
        var user = User.builder().id(1l).build();
        var userReport = UserReportResponse.builder().lastMessageText("text").build();
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        Mockito.when(mapper.mapToReportFrom(user)).thenReturn(userReport);
        var actual = userService.getUserReports();
        assertEquals(1, actual.size());
        assertEquals("text", actual.get(0).getLastMessageText());
    }

    @Test
    void getUserReport() {
        var user = User.builder().id(2l).build();
        var userReport = UserReportResponse.builder().lastMessageText("text").build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Mockito.when(mapper.mapToReportFrom(user)).thenReturn(userReport);
        var actual = userService.getUserReport(1l);
        assertEquals("text", actual.get().getLastMessageText());
    }
}