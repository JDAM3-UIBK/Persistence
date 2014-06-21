package at.compare.init;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import at.compare.model.LoggedRoute;
import at.compare.model.LoggedRouteValidator;



@Configuration
@EnableWebMvc
@ComponentScan("at.compare")
@EnableJpaRepositories("at.compare.repository")
public class WebAppConfig extends WebMvcConfigurerAdapter{
	
	
	@Bean
    public DataSource dataSource() {
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/comparedb");
        dataSource.setUsername("compare");
        dataSource.setPassword("jdam");
        
        return dataSource;
    }
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {

	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("at.compare.model");
	    factory.setDataSource(dataSource());
	    factory.afterPropertiesSet();
	    
	    return factory.getObject();
	}
	 @Bean
	 public PlatformTransactionManager transactionManager() {

	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory());
	    return txManager;
	 }
	 @Bean 
	 public HibernateExceptionTranslator hibernateExceptionTranslator(){ 
	      return new HibernateExceptionTranslator(); 
	 }
	
	
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	@Bean
	public HttpHeaders headers(){
		HttpHeaders tmp = new HttpHeaders();
		tmp.add("Content-Type", "application/json; charset=utf-8");
		return tmp;
	}
	
	
}
