package com.aukustomx.infra.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "testautoEntityManagerFactory",
        transactionManagerRef = "testautoTransactionManager",
        basePackages = {"com.aukustomx"}
)
public class DataBaseConfiguration {

    @Bean(name = "testautoDataSource")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/dbname");
        return dataSource;
    }

    @Bean(name = "testautoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPackagesToScan("com.aukustomx");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setDataSource(dataSource());

        final Properties jpaProps = new Properties();
        jpaProps.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        entityManagerFactoryBean.setJpaProperties(jpaProps);

        return entityManagerFactoryBean;
    }

    @Bean(name = "testautoTransactionManager")
    public PlatformTransactionManager testautoTransactionManager(
            @Qualifier("testautoEntityManagerFactory") EntityManagerFactory testautoEntityManagerFactory) {

        return new JpaTransactionManager(testautoEntityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
