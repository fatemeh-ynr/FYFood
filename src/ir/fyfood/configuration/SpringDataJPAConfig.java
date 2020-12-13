package ir.fyfood.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@EnableTransactionManagement
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {"ir.maktab.dining.repository.dao"})
@PropertySource({"classpath:/ir/fyfood/properties/application.properties"})
public class SpringDataJPAConfig {

    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource(){
        final DriverManagerDataSource dataSourceConfig = new DriverManagerDataSource();
        dataSourceConfig.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSourceConfig.setUrl(environment.getProperty("jdbc.url"));
        dataSourceConfig.setUsername(environment.getProperty("jdbc.username"));
        dataSourceConfig.setPassword(environment.getProperty("jdbc.password"));
        return dataSourceConfig;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("ir.fyfood.repository.entity");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        jpaProperties.put("hibernate.hbm2ddl.auto",env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.show_sql",env.getRequiredProperty("hibernate.show_sql"));
        jpaProperties.put("hibernate.format_sql",env.getRequiredProperty("hibernate.format_sql"));
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
