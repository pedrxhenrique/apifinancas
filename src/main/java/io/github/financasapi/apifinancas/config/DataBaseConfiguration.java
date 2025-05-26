package io.github.financasapi.apifinancas.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Value("jdbc:postgresql://localhost:5433/financas_db")
    String url;
    @Value("postgres")
    String user;
    @Value("123456")
    String password;
    @Value("org.postgresql.Driver")
    String driver;

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setDriverClassName(driver);
        return new HikariDataSource(config);
    }
}
