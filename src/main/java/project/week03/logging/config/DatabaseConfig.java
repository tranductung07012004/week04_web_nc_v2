package project.week03.logging.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "project.week03.logging.repository")
@EnableTransactionManagement
public class DatabaseConfig {
}