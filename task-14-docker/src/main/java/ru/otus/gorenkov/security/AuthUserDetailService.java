package ru.otus.gorenkov.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.gorenkov.models.User;
import ru.otus.gorenkov.repositories.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.getByUsername(username);

        User dbUser = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));

        return org.springframework.security.core.userdetails.User.builder()
//                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .username(dbUser.getUsername())
                .password((dbUser.getPassword()))
                .roles(dbUser.getRoles().toArray(String[]::new))
                .build();
    }
}
