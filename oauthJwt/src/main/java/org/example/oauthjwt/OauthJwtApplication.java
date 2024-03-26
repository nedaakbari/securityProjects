package org.example.oauthjwt;

import org.example.oauthjwt.config.RsaKeyConfigProperties;
import org.example.oauthjwt.user.User;
import org.example.oauthjwt.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class OauthJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthJwtApplication.class, args);
    }
    @Bean
    public CommandLineRunner initializeUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {

            User user = new User();
            user.setUsername("exampleuser");
            user.setEmail("example@gmail.com");
            user.setPassword(passwordEncoder.encode("examplepassword"));
            user.setRole("ROLE_ADMIN");

            // Save the user to the database
            userRepository.save(user);

        };
    }
}
