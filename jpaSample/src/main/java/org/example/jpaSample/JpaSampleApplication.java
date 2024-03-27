package org.example.jpaSample;

import org.example.jpaSample.user.User;
import org.example.jpaSample.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JpaSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaSampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {

            User user = new User();
            user.setUsername("neda");
            user.setEmail("example@gmail.com");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRole("ROLE_USER");

            // Save the user to the database
            userRepository.save(user);

        };
    }
}
