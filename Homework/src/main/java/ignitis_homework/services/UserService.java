package ignitis_homework.services;

import ignitis_homework.dto.UserReportResponse;
import ignitis_homework.dto.UserResponse;
import ignitis_homework.entities.User;
import ignitis_homework.mappers.UserMapper;
import ignitis_homework.repositories.AuthorityRepository;
import ignitis_homework.repositories.UserRepository;
import ignitis_homework.security.dto.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper mapper;

    public UserResponse addUser(AddUserRequest request) {
        var authority = authorityRepository.findAllByRoles(request.getRoles());
        var user = mapper.mapFrom(request, authority);
        return mapper.mapFrom(userRepository.save(user));
    }

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
        return userRepository.findAll().stream()
                .map(mapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<UserReportResponse> getUserReports() {
        return userRepository.findAll().stream()
                .map(mapper::mapToReportFrom)
                .collect(Collectors.toList());
    }

    public Optional<UserReportResponse> getUserReport(Long userId) {
        return userRepository.findById(userId).map(mapper::mapToReportFrom);
    }
}
