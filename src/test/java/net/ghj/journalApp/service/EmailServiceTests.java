package net.ghj.journalApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class EmailServiceTests {

    @Mock
    EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMail() {
        emailService.sendEmail("golakiyahit0@gmail.com", "Testing java mail sender", "Hii, this is the testing mail for learning java mail sender.");

        verify(emailService, times(1)).sendEmail("golakiyahit0@gmail.com", "Testing java mail sender", "Hii, this is the testing mail for learning java mail sender.");
    }
}
