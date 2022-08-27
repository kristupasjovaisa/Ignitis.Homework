package ignitis_homework.services;

import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.User;
import ignitis_homework.mapper.UserMapper;
import ignitis_homework.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Transactional
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
            return true;
        }
        return false;
    }

    public Optional<UserResponse> getUser(Long id) {
        return userRepository.findById(id).map(mapper::mapFrom);
    }

    public List<UserResponse> getAllUsers() {
        var list = userRepository.findAll();
        if (list != null) {
            return userRepository.findAll().stream()
                    .map(mapper::mapFrom)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
