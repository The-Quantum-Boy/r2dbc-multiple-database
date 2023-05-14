package com.sumit.r2dbc.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactoryBean;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@EnableR2dbcRepositories(
        basePackages = {"com.sumit.r2dbc.db1.repositories"},
        repositoryFactoryBeanClass = R2dbcRepositoryFactoryBean.class,
        entityOperationsRef = "db1tmplt"
)
public class Db1Config extends AbstractR2dbcConfiguration {


    @Bean(name = "db1tmplt")
    public R2dbcEntityTemplate r2dbcEntityTemplate(@Qualifier("db1Client") DatabaseClient databaseClient) {
        return new R2dbcEntityTemplate(databaseClient, new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE));
    }


    @Bean(name = "db1ConnectionFactory")
    public ConnectionFactory db1connectionFactory() {
        System.out.println("Db1Config.connectionFactory");
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(DRIVER, "postgresql")
                        .option(HOST, "localhost")
                        .option(USER, "postgres")
                        .option(PASSWORD, "sumit")
                        .option(DATABASE, "database1")
                        .build());
    }

    @Bean(name = "db1Client")
    public DatabaseClient databaseClient(@Qualifier("db1ConnectionFactory") ConnectionFactory connectionFactory){
        return DatabaseClient.create(connectionFactory);
    }

//    @Bean(name = "db1TransactionManager")
//    public ReactiveTransactionManager db2TransactionManager(@Qualifier("db1ConnectionFactory") ConnectionFactory connectionFactory) {
//        return new R2dbcTransactionManager(connectionFactory);
//    }



    @Override
    public ConnectionFactory connectionFactory() {
        return db1connectionFactory();
    }


    public Db1Config(){
        System.out.println("Db1Config.Db1Config");
    }
}
