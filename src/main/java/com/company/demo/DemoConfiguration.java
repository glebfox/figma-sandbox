package com.company.demo;

import com.company.demo.datasource.DemoLiquibase;
import com.company.demo.datasource.DemoRoutingDataSource;
import io.jmix.core.Stores;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
public class DemoConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "routing.datasource")
    public DataSource dataSource() {
        return new DemoRoutingDataSource();
    }

    @Bean("demo_SessionDataSource")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @ConfigurationProperties(prefix = "session.datasource")
    public DataSource sessionDataSource() {
        return new BasicDataSource();
    }

    @Bean(name = "demo_Liquibase")
    @Primary
    public SpringLiquibase liquibase(DataSource dataSource,
                                     LiquibaseProperties properties,
                                     LiquibaseChangeLogProcessor processor) {

        DemoLiquibase liquibase = new DemoLiquibase();

        liquibase.setChangeLogContent(processor.createMasterChangeLog(Stores.MAIN));

        liquibase.setDataSource(dataSource);
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());

        return liquibase;
    }
}
