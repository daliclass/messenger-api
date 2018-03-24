package com.opendebate.messenger;

import com.opendebate.messenger.discussion.DiscussionStore;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class MessengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class);
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        final String DATA_SOURCE  = "jdbc:h2:file:./target/chat-history";
        final String USER = "sa";
        final String PASSWORD = null;

        Flyway flyway = new Flyway();
        flyway.setDataSource(DATA_SOURCE, USER, PASSWORD);
        flyway.clean();
        return flyway;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("jimbo");
    }
}
