package ignitis_homework.security.service;

import ignitis_homework.repositories.UserRepository;
import ignitis_homework.security.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmailWithAuthorities(username)
                .map(user -> mapper.mapUserRoleFrom(user))
                .orElseThrow(() -> new UsernameNotFoundException("'" + username + "' not found!"));
    }
}
