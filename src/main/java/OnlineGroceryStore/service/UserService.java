package OnlineGroceryStore.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import OnlineGroceryStore.entity.User;
import OnlineGroceryStore.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Convert old plain-text passwords to BCrypt
    @PostConstruct
    public void migrateOldPasswords() {

        List<User> users = userRepository.findAll();

        for (User user : users) {

            String password = user.getPassword();

            if (password != null
                    && !password.startsWith("$2a$")
                    && !password.startsWith("$2b$")
                    && !password.startsWith("$2y$")) {

                user.setPassword(
                        passwordEncoder.encode(password)
                );

                userRepository.save(user);

                System.out.println(
                        "Password migrated to BCrypt for user: "
                        + user.getEmail()
                );
            }
        }
    }

    // Register new user
    public User register(User user) {

        if (user.getName() == null
                || user.getName().trim().isEmpty()) {

            throw new RuntimeException("Name is required");
        }

        if (user.getEmail() == null
                || user.getEmail().trim().isEmpty()) {

            throw new RuntimeException("Email is required");
        }

        if (user.getPassword() == null
                || user.getPassword().length() < 4) {

            throw new RuntimeException(
                    "Password must contain at least 4 characters"
            );
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        // Encrypt password before saving
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        // Default role
        if (user.getRole() == null
                || user.getRole().trim().isEmpty()) {

            user.setRole("CUSTOMER");
        }

        return userRepository.save(user);
    }

    // Login user
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        return user;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElse(null);
    }
}