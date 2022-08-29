package ignitis_homework.services;

import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.User;
import ignitis_homework.mappers.UserMapper;
import ignitis_homework.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper mapper;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Shoud delete User by id ")
    void deleteUser() {
        User user = User.builder()
                .id(2l)
                .build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        boolean actual = userService.deleteUser(1l);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(2l);
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    @DisplayName("Shoud find user by Id")
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
        Assertions.assertThat(actual.get().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("Shoud find all users")
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        User user = User.builder().id(1l).build();
        users.add(user);

        UserResponse userResponses = UserResponse.builder().id(2l).build();

        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(mapper.mapFrom(user)).thenReturn(userResponses);
        List<UserResponse> actual = userService.getAllUsers();
        Assertions.assertThat(actual.get(0).getId()).isEqualTo(2l);
        Assertions.assertThat(actual.size()).isEqualTo(1);
    }
}