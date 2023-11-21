package com.flywaydb.FlyWayDemo.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManagerFactoryBean",
        basePackages = {"com.flywaydb.FlyWayDemo.admin.repo"},
        transactionManagerRef = "secondTransactionManager"
)

public class MyAdminDbConfig {

    private final Environment environment;
    @Autowired
    public MyAdminDbConfig(Environment environment) {
        this.environment = environment;
    }


    //datasource

    @Bean(name = "adminSecondDatabase")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.admin")
    public DataSource dataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.admin.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.admin.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.admin.password"));
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.admin.driver-class-name")));


        return dataSource;

    }


    //entityManagerFactory

    @Bean(name ="secondEntityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

        bean.setDataSource(dataSource());

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","update");
        bean.setJpaPropertyMap(props);
        bean.setPackagesToScan("com.flywaydb.FlyWayDemo.admin.entities");

        return bean;
    }


    //platformTransactionManager
    @Bean(name = "secondTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(){

        JpaTransactionManager manager = new JpaTransactionManager();

        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return manager;

    }
}
