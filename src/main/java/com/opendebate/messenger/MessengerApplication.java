package com.opendebate.messenger;

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
    public Flyway flyway(Config config) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(config.getDatasource(), config.getUsername(), config.getPassword());
        flyway.clean();
        return flyway;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(Config config) {
        return Persistence.createEntityManagerFactory(config.getPersistenceUnitName());
    }
}
