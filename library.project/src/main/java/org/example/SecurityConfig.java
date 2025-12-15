package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
public class SecurityConfig {

    // Custom AuthenticationProvider, чтобы убрать ошибки с AuthenticationManager и типами
    @Bean
    public AuthenticationManager authenticationManager(UsersXmlService users) {
        AuthenticationProvider provider = new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                Object credentials = authentication.getCredentials();
                String password = credentials != null ? credentials.toString() : "";

                Optional<UsersXmlService.User> opt = users.find(username);
                if (!opt.isPresent()) {
                    throw new BadCredentialsException("Нет такого пользователя");
                }

                UsersXmlService.User u = opt.get();
                if (!u.getPassword().equals(password)) {
                    throw new BadCredentialsException("Неверный пароль");
                }

                List<GrantedAuthority> auths =
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + u.getRole()));

                return new UsernamePasswordAuthenticationToken(username, password, auths);
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };

        return new ProviderManager(Collections.singletonList(provider));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Правильный способ отключить CSRF в Security 6 (без устаревших вызовов)
                .csrf(csrf -> csrf.disable())
                // Новый API маршрутов (authorizeHttpRequests + requestMatchers)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/librarian/**").hasRole("LIBRARIAN")
                        .requestMatchers("/reader/**").hasRole("READER")
                        .anyRequest().authenticated()
                )
                // Настройки формы логина
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/post-login", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                );

        return http.build();
    }
}
