package com.revenat.myresume.application.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.revenat.myresume.application.config.transaction.TransactionalEmulationAspect;
import com.revenat.myresume.application.service.notification.NotificationMessage;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Configuration
@PropertySource({
	"classpath:/properties/application.properties",
	"classpath:logic.properties"
})
@ComponentScan({
	"com.revenat.myresume.application.service.profile.impl",
	"com.revenat.myresume.application.service.notification.impl",
	"com.revenat.myresume.application.service.task.impl",
	"com.revenat.myresume.application.transformer",
	"com.revenat.myresume.application.provider.impl",
	"com.revenat.myresume.application.template.impl",
	"com.revenat.myresume.application.generator.impl"
})
@EnableAspectJAutoProxy
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
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public static ConverterRegistryPostProcessor converterRegistryPostProcessor() {
		return new ConverterRegistryPostProcessor();
	}
	
	
	@Bean
	public TransactionalEmulationAspect transactionalEmulationAspect() {
		return new TransactionalEmulationAspect();
	}
	
	/**
	 * {@link PropertiesFactoryBean} is a FactoryBean implementation which reads a properties file
	 * and exposes that as an {@code Properties} object in the applicationcontext.
	 */
	@Bean
	public PropertiesFactoryBean properties() {
		PropertiesFactoryBean properties = new PropertiesFactoryBean();
		properties.setLocations(new ClassPathResource("logic.properties"));
		return properties;
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
	public Map<String, NotificationMessage> getNotificationTemplates(@Value("${notification.config.path}") String configPath)
			throws IOException {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.setValidating(false);
		reader.loadBeanDefinitions(getResource(configPath));
		return CommonUtils.getSafeMap(beanFactory.getBeansOfType(NotificationMessage.class));
	}

	private Resource getResource(String resourcePath) throws IOException {
		for (Resource resource : getResourceCandidates(resourcePath)) {
			if (resource.exists()) {
				return resource;
			}
		}
		throw new IOException("Resource " + resourcePath + " not found");
	}
	
	private Iterable<Resource> getResourceCandidates(String resourcePath) {
		List<Resource> resourceCandidates = new ArrayList<>();
		resourceCandidates.add(new PathResource(resourcePath));
		resourceCandidates.add(new ClassPathResource(resourcePath));
		try {
			resourceCandidates.add(new UrlResource(resourcePath));
		} catch (MalformedURLException e) {
			// ignore
		}
		return resourceCandidates;
	}
}
