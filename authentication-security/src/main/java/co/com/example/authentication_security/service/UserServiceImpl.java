package co.com.example.authentication_security.service;

import co.com.example.authentication_security.entity.User;
import co.com.example.authentication_security.exceptions.AuthenticationException;
import co.com.example.authentication_security.exceptions.UserNotFoundException;
import co.com.example.authentication_security.repository.UserRepository;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User authenticate(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: ".concat(username)));


        if (user != null &&
                new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2)
                        .matches(password, user.getPassword())) {
            return user;
        } else {
            throw new AuthenticationException("Invalid username or password");
        }
    }

    @Override
    public void saveUser(User user) {

        user.setPassword(new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2)
                .encode(user.getPassword()));
        userRepository.save(user);

    }
}
