package ir.fyfood.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ir.fyfood.repository.dao")
@ComponentScan(basePackages = "ir.fyfood")
@Import(SpringDataJPAConfig.class)
public class SpringContext {

}
