package net.ghj.journalApp.service;


import net.ghj.journalApp.entity.User;
import net.ghj.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mongodb.assertions.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    @Disabled
    @Test
    public void testFindByUserName(){
        assertEquals(4,2+2);

        User user = userRepository.findByUserName("hit");
        assertFalse(user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,5",
            "2,2,5",
            "2,7,9",
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected,a+b);
    }
}
