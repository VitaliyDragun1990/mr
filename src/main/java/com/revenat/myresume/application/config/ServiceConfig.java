package com.revenat.myresume.application.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.revenat.myresume.application.model.NotificationMessage;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Configuration
@PropertySource("classpath:application.properties") // for Environment.getProperty(...)
@EnableScheduling
@ComponentScan({
	"com.revenat.myresume.application.service.profile.impl",
	"com.revenat.myresume.application.service.notification.impl",
	"com.revenat.myresume.application.service.task.impl",
	"com.revenat.myresume.application.transformer.impl",
	"com.revenat.myresume.application.provider.impl",
	"com.revenat.myresume.application.template.impl",
	"com.revenat.myresume.application.generator.impl"
})
public class ServiceConfig {
	
	@Value("${executorService.threadCount}")
	private String threadCount;

	/**
	 * http://docs.spring.io/autorepo/docs/spring/4.2.5.RELEASE/spring-framework-reference/html/beans.html
	 * 
	 * Also, be particularly careful with BeanPostProcessor and BeanFactoryPostProcessor definitions via @Bean. 
	 * Those should usually be declared as static @Bean methods, not triggering the instantiation of their containing configuration class. 
	 * Otherwise, @Autowired and @Value won’t work on the configuration class itself since it is being created as a bean instance too early.
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() throws IOException {
		PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
		conf.setLocations(getResources());
		return conf;
	}
	
	@Bean
	public static ConverterRegistryPostProcessor converterRegistryPostProcessor() {
		return new ConverterRegistryPostProcessor();
	}
	
	@Bean("defaultExecutorService")
	public ExecutorServiceFactoryBean executorService() {
		ExecutorServiceFactoryBean executorServiceFactoryBean = new ExecutorServiceFactoryBean();
		executorServiceFactoryBean.setThreadCount(threadCount);
		return executorServiceFactoryBean;
	}
	
	@Bean
	public ConversionServiceFactoryBean conversionService() {
		return new ConversionServiceFactoryBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	@Bean
	public Map<String, NotificationMessage> getNotificationTemplates(@Value("${notification.config.path}") String configPath) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.setValidating(false);
		reader.loadBeanDefinitions(new PathResource(configPath));
		return CommonUtils.getSafeMap(beanFactory.getBeansOfType(NotificationMessage.class));
	}

	private static Resource[] getResources() {
		return new Resource[] {new ClassPathResource("application.properties"), new ClassPathResource("logic.properties")};
	}
}
