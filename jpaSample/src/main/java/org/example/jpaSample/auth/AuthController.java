package org.example.jpaSample.auth;


import org.example.jpaSample.user.User;
import org.example.jpaSample.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO.LoginRequest userLogin) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userLogin.username(),
                        userLogin.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUser userDetails = (AuthUser) authentication.getPrincipal();


        log.info("Token requested for user :{}", authentication.getAuthorities());

        AuthDTO.Response response = new AuthDTO.Response("User logged in successfully", "");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ROLE_USER')")//todo should have in database ROLE_USER  instead USER
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //https://stackoverflow.com/questions/42146110/when-should-i-prefix-role-with-spring-security
    //10
    //
    //Automatic ROLE_ prefixing
    //As Spring Security 3.x to 4.x migration guide states:
    //
    //Spring Security 4 automatically prefixes any role with ROLE_. The changes were made as part of SEC-2758
    //
    //With that being said, the ROLE_ prefix in the following annotation is redundant:
    //
    //@PreAuthorize("hasRole('ROLE_USER')")
    //Since you're calling hasRole method, the fact that you're passing a role is implied. Same is true for the following expression:
    //
    //antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
    //But for the:
    //
    //new SimpleGrantedAuthority("ROLE_ADMIN")
    //Since this is an authority, not a role, you should add the ROLE_ prefix (If your intent is to create a role!). Same is true for calling public InMemoryUserDetailsManager(Properties users) constructor, since it's using an authority internally.
}
