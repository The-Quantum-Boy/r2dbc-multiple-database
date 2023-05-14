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
        basePackages = {"com.sumit.r2dbc.db2.repositories"},
        repositoryFactoryBeanClass = R2dbcRepositoryFactoryBean.class,
        entityOperationsRef = "db2tmplt"
)
public class Db2Config extends AbstractR2dbcConfiguration {

    @Bean(name = "db2tmplt")
    public R2dbcEntityTemplate r2dbcEntityTemplate(@Qualifier("db2Client") DatabaseClient databaseClient) {
        return new R2dbcEntityTemplate(databaseClient, new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE));
    }


    @Bean(name = "db2ConnectionFactory")
    public ConnectionFactory db2connectionFactory(){
        System.out.println("Db2Config.connectionFactory");
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(DRIVER, "postgresql")
                        .option(HOST, "localhost")
                        .option(USER, "postgres")
                        .option(PASSWORD, "sumit")
                        .option(DATABASE, "database2")
                        .build());
    }

    @Bean(name = "db2Client")
    public DatabaseClient databaseClient(@Qualifier("db2ConnectionFactory") ConnectionFactory connectionFactory){
        return DatabaseClient.create(connectionFactory);
    }

//    @Bean(name = "db2TransactionManager")
//    public ReactiveTransactionManager db2TransactionManager(@Qualifier("db2ConnectionFactory") ConnectionFactory connectionFactory) {
//        return new R2dbcTransactionManager(connectionFactory);
//    }



    @Override
    public ConnectionFactory connectionFactory() {
        return db2connectionFactory();
    }


    public Db2Config(){
        System.out.println("Db2Config.Db2Config");
    }

}
