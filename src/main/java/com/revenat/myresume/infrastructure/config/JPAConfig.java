package com.revenat.myresume.infrastructure.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource({
	"classpath:/properties/jpa.properties",
})
@EnableTransactionManagement
@EnableJpaRepositories("com.revenat.myresume.infrastructure.repository.storage")
public class JPAConfig {

	@Autowired
	private Environment env;
	
	/**
	 * http://docs.spring.io/autorepo/docs/spring/4.2.5.RELEASE/spring-framework-reference/html/beans.html
	 * 
	 * By default, beans defined using Java config that have a public close or shutdown method are automatically enlisted with a destruction callback.
	 */
	
	@Bean(/*destroyMethod="close"*/)
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
		dataSource.setUrl(env.getRequiredProperty("db.url"));
		dataSource.setUsername(env.getRequiredProperty("db.username"));
		dataSource.setPassword(env.getRequiredProperty("db.password"));
		dataSource.setInitialSize(env.getRequiredProperty("db.pool.initSize", Integer.class));
		dataSource.setMaxTotal(env.getRequiredProperty("db.pool.maxSize", Integer.class));
		return dataSource;
	}
	
	private Properties hibernateProperties() {
		Properties props = new Properties();
		props.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		props.put("javax.persistence.validation.mode", "none");
		props.put("org.hibernate.flushMode", "COMMIT"); // TODO: ????
		props.put("show_sql", "true");
		props.put("format_sql", "true");
		props.put("use_sql_comments", "true");
		return props;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setPackagesToScan("com.revenat.myresume.domain.entity");
		entityManagerFactory.setJpaProperties(hibernateProperties());
		return entityManagerFactory;
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}
