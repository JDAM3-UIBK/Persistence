package at.compare.test.init;


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
/**
 * 
 * Spring MVC Framework Configuration for Data Access from and to Database
 * ComponentScan looks in Packages for Controllers
 * EnableJpaRepositories looks for repositories 
 * In Repositories Data from Database is saved
 * !!!Only for Testing!!!
 * Original at:
 * @see at.compare.init.WebAppConfig 
 * 
 * @author Joachim Rangger
 */

@Configuration
@EnableWebMvc
@ComponentScan("at.compare")
@EnableJpaRepositories("at.compare.repository")
public class WebAppConfigTest extends WebMvcConfigurerAdapter{
	
	/**
	 * Initialize Connection to Database
	 * @return  DriverManagerDataSource dataSource 
	 */
	@Bean
    public DataSource dataSource() {
        
		
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:butterfly");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        
        return dataSource;
    }
	
	/**
	 * setDataSource @see #dataSource()
	 * PackagesToScan look for Entities: @see at.compare.model.User and @see at.compare.model.LoggedRoute 
	 * vendorAdapter: set Hibernate as persistence framework
	 * vendorAdapter.setGenerateDdl: Generates hibernate.hbm2ddl
	 * 
	 * @return EntityManagerFactory factory.getObject()
	 */
	
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
	
	/**
	 * Manager for Entity interaction with Database
	 * @return JpaTransactionManager txManager
	 */
	 @Bean
	 public PlatformTransactionManager transactionManager() {

	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory());
	    return txManager;
	 }
	 /**
	  * Important, without Hibernate does not work
	  * @return HibernateExceptionTranslator
	  */
	 @Bean 
	 public HibernateExceptionTranslator hibernateExceptionTranslator(){ 
	      return new HibernateExceptionTranslator(); 
	 }
	 /**
		 * Path to JSP Directory
		 * @return ViewResolver 
		 */
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	/**
	 * @return HttpHeader: "Content-Type", "application/json; charset=utf-8"
	 */
	@Bean
	public HttpHeaders headers(){
		HttpHeaders tmp = new HttpHeaders();
		tmp.add("Content-Type", "application/json; charset=utf-8");
		return tmp;
	}
	
	
}
