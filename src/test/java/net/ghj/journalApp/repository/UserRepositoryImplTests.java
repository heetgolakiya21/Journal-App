package net.ghj.journalApp.repository;

import net.ghj.journalApp.repository.Impl.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    UserRepositoryImpl userRepository;

    @Test
    public void testFindUser() {
        Assertions.assertNotNull(userRepository.getUserForSA());
    }
}
