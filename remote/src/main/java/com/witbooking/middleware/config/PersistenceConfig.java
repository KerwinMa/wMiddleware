package com.witbooking.middleware.config;

import com.witbooking.middleware.db.router.BookingEngineRoutingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by mongoose on 20/05/15.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.witbooking.middleware.db.repository")
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean
    public DataSource dataSource() throws SQLException {
        BookingEngineRoutingDataSource bookingEngineRoutingDataSource = new BookingEngineRoutingDataSource();
        bookingEngineRoutingDataSource.setTargetDataSources(bookingEngineRoutingDataSource.getDynamicTargetDataSources());
        bookingEngineRoutingDataSource.afterPropertiesSet();
        return bookingEngineRoutingDataSource;
    }

}
