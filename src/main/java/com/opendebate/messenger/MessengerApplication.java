package com.opendebate.messenger;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
@EnableScheduling
public class MessengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class);
    }

    /* Usually imported by the @EnableScheduling however @EnableWebsocket also imports a TaskScheduler that
    * is missing some methods, so this is the workaround
    */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
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

    @Bean
    @Scope(scopeName = "prototype")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }


}
