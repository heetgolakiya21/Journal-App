package net.ghj.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    EmailService emailService;

    @Test
    void testSendMail() {
        emailService.sendEmail("golakiyahit0@gmail.com","Testing java mail sender","Hii, this is the testing mail for learning java mail sender.");
    }
}
