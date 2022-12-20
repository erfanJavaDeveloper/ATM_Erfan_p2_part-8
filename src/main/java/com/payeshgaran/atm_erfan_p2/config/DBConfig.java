package com.payeshgaran.atm_erfan_p2.config;


import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.payeshgaran.atm_erfan_p2")
@PropertySource("classpath:application-h2.properties")
@RequiredArgsConstructor
//@EnableTransactionManagement
public class DBConfig {


    @Bean
    public HibernateTemplate hibernateTemplate() {
        return new HibernateTemplate(sessionFactory());
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(getDataSource());
        lsfb.setPackagesToScan("com.payeshgaran.atm_erfan_p2.entity");
        lsfb.setHibernateProperties(hibernateProperties());
        try {
            lsfb.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lsfb.getObject();
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean
    public HibernateTransactionManager hibTransMan() {
        return new HibernateTransactionManager(sessionFactory());
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.show_sql", true);
//        properties.put("hibernate.show_sql", true);
//        properties.put("server.port", 8181);
        return properties;
    }

//
//    @Bean
//    public HibernateTemplate hibernateTemplate() {
//        return new HibernateTemplate(sessionFactory());
//    }
//
//    @Bean
//    public SessionFactory sessionFactory() {
//        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
//        lsfb.setPackagesToScan("com.example.atm_erfanadine_project2.entity");
//        lsfb.setHibernateProperties(hibernateProperties());
//        try {
//            lsfb.afterPropertiesSet();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return lsfb.getObject();
//    }
//
//
//
//    @Bean
//    public HibernateTransactionManager hibTransMan() {
//        return new HibernateTransactionManager(sessionFactory());
//    }
//
//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
//        properties.put("hibernate.hbm2ddl.auto", "create-drop");
//        properties.put("hibernate.show_sql",true);
//        return properties;
//    }
}
