package org.example.basicinmemmory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // User Creation
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        // InMemoryUserDetailsManager
        UserDetails admin = User.withUsername("Amiya")
                .password(encoder.encode("123"))
                .roles("ADMIN", "USER")
                .build();

        UserDetails user = User.withUsername("Ejaz")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    // Configuring HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/auth/welcome").permitAll()
                                .requestMatchers("/auth/user/**").authenticated()
                                .requestMatchers("/auth/admin/**").authenticated()
                ).formLogin(Customizer.withDefaults())
                .build();


//        return http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/auth/welcome").permitAll()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated()
//                .and()
//                .authorizeHttpRequests().requestMatchers("/auth/admin/**").authenticated()
//                .and().formLogin()
//                .and().build();
//    }

        //http
        //    .httpBasic()
        //    .and()
        //    .authorizeRequests()
        //    .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
    }

    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //SecurityContext securityContext = new SecurityContextImpl();
    //final Properties users = new Properties();
    //users.put("joe","secret,ADMIN,enabled");            <-- here
    //InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(users);

    //Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
    //grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));         <-- here
    //AnonymousAuthenticationToken anonymousAuthenticationToken = new AnonymousAuthenticationToken("test", manager.loadUserByUsername("joe"), grantedAuthorities);
    //        securityContext.setAuthentication(anonymousAuthenticationToken);
    //        SecurityContextHolder.setContext(securityContext);
}