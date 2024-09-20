package net.ghj.journalApp.config;

import net.ghj.journalApp.filter.JwtFilter;
import net.ghj.journalApp.service.Impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// This annotation signals spring to enable its web security support. This is what makes your application secured, It's used in conjunction with @Configuration
@Profile("dev")
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/journal/**", "/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable) // Cross-Site Request Forgery - CSRF
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}

/*  * Spring Security is a powerful and highly customizable security framework that is often used in spring boot applications to handle authentication and authorization.
    * Authentication: The process of verifying a user's identity. (e.g. username and password)
    * Authorization: The process of granting or denying access to specific resources or actions based on the authenticated user's role and permissions.
    * Filter: A Filter is an object that is invoked at the preprocessing and postprocessing of a request.
    * Once the dependency is added, Spring Boot's autoconfiguration feature will automatically apply security to the application.
    * By default, Spring Security uses HTTP Basic Authentication.
    * The client sends an authorization header Authorization: Basic <encoded-string> The server decodes the string, extract the username and password, and verify them. If they're correct, access is granted. Otherwise, an "Unauthorized" response is sent back.
    * Encoding Credentials are combined into a string like username:password which is then encoded using Base64
    * By default, all endpoints will be secured.
    * Spring security will generate a default user with a random password that is printed in the console logs on startup.
    * You can also configure username & password in your application.properties

    * WebSecurityConfigureAdapter is a utility class in the spring security framework that provides default configurations and allows customization of certain features. By extending it, you can configure and customize Spring Security for your application needs.
    * configurer() methods provides a way to configure how request are secured. It defines how request matching should be done and what security actions should be applied.
    * http.authorizeRequests(): This tells Spring Security to start authorizing the requests.
    * .antMatchers("/hello").permitAll(): This part specifies the HTTP requests matching the path /hello should be permitted(allowed) for all users, whether they are authenticated or not.
    * .anyRequest().authenticated(): This is more general matcher that specifies any request (not already matched by previous matchers) should be authenticated, meaning users have to provide valid credentials to access these endpoints.
    * .and(): This is a method to join several configuration. It helps to continue the configuration from the root (HttpSecurity).
    * .formLogin(): This enables form-based authentication. By default, it will provide a form for the user to enter their username and password. If the user is not authenticated, and they try to access a secured endpoints, they'll be redirected to the default login form.
    * You can access /hello without any authentication. However, if you try to access another endpoints, you'll be redirected to a login form.
    * When we use the .formLogin() method in our security configuration without specifying .loginPage("/custom-path"), the default login page becomes active.
    * Spring Security provides an in-built controller that handles the /login path. This controller is responsible for rendering the default login form when a GET request is made to /login.
    * By default, Spring Security also provides logout functionality. When .logout() is configured, a POST requests to /logout will log the user out and invalidate their session.
    * Basic Authentication, by its design, is stateless.
    * Some application do mix Basic Authentication with session management for various reasons. This isn't standard behaviour and requires additional setup and logic. In such scenarios, once the user's credentials are verified via Basic Authentication, a session might be established, and the client
        is provided a session cookie. This way, the client won't need to send the Authorization header with every request, and the server can rely on the session cookie to identify the authenticated user.
    * When you log in with Spring Security, it manages your authentication across multiple requests, despite HTTP being stateless.
    * 1) Session Creation: After successful authentication, an HTTP session is formed. Your authentication details are stored in this session.
    * 2) Session Cookie: A JSESSIONID cookie is sent to your browser, which gets sent back with subsequent requests, helping the server recognize your session.
    * 3) SecurityContext: Using the JSESSIONID, Spring Security fetches your authentication details for each request.
    * 4) Session Timeout: Sessions have a limited life. If you-re inactive past this limit, you're logged out.
    * 5) Logout: When logging out, your session ends, and the related cookie is removed.
    * 6) Remember-Me: Spring Security can remember you even after the session ends using a different persistent cookie (typically have a longer lifespan).
    * In essence, Spring Security leverages sessions and cookie, mainly JSESSIONID, to ensure you remain authenticated across requests.

    * We want our Spring Boot application to authenticate users based on their credentials stored in a MongoDB database.
    * This means that our users and their passwords (hashed) will be stored in MongoDB, and when a user tries to log in, the system should check the provided credentials against what's stored in database.
    * A User entity to represent the user data model.
    * A repository UserRepository to interact with MongoDB.
    * UserDetailsService implementation to fetch user details.
    * A configuration SecurityConfig to integrate everything with Spring Security
 */