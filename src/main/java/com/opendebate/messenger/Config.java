package com.opendebate.messenger;

import com.opendebate.messenger.streaming.WebSocketConfig;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@PropertySource("classpath:application.yml")
@Data
@NoArgsConstructor public class Config {
    private String persistenceUnitName;
    private String datasource;
    private String username;
    private String password;
}
