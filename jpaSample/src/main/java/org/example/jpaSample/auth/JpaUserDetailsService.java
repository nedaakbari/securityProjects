package org.example.jpaSample.auth;

import lombok.RequiredArgsConstructor;
import org.example.jpaSample.user.User;
import org.example.jpaSample.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found: " + username));

        AuthUser authUser = new AuthUser(user);
        authUser.setUserId(user.getUserId());
        authUser.setUsername(username);
        authUser.setRole(user.getRole());
        authUser.setEmail(user.getEmail());
        authUser.setPassword(user.getPassword());
        return authUser;

    }
}
