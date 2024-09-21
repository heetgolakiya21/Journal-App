package net.ghj.journalApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
/*	Above Annotation works like below 3 annotations, so we can say that @SpringBootApplication contains below 3 anno. .
	@Configuration
	@EnableAutoConfiguration
	@ComponentScan - It is used to Scan Classes and add it into IOC(Inversion of Control) Container, so whenever I need, it doesn't need to create an object of that class which are in IOC. because it already created by @ComponentScan.	*/

//@Component Annotation use to create Bean. Here, Bean means Objects.
@EnableTransactionManagement
@EnableScheduling
public class JournalAppApplication {

    private static final Logger logger = LoggerFactory.getLogger(JournalAppApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JournalAppApplication.class, args);

        logger.info("Application Started on {}", Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }

    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}

/*	> Spring IOC(Inversion of Control) Container:-
	:- It is a predefined programs or component and responsible to creating objects, holding them in a memory, injecting them into another object as required (Dependency Injection).
	:- ApplicationContext represents the IOC container.	*/