package net.ghj.journalApp.service;

import net.ghj.journalApp.entity.User;
import net.ghj.journalApp.repository.UserRepository;
import net.ghj.journalApp.service.Impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.builder;


public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTests() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn((User) builder().username("riyal").password("eyrueyrue").roles(String.valueOf(new ArrayList<>())).build());
        UserDetails user = userDetailsServiceImpl.loadUserByUsername("hit");
        Assertions.assertNotNull(user);
    }
}
