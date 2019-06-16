package com.revenat.myresume.application.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan({
	"com.revenat.myresume.application.service.impl",
	"com.revenat.myresume.application.converter"
})
public class ServiceConfig {

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
	
	@Bean
	public ConversionServiceFactoryBean conversionService() {
		return new ConversionServiceFactoryBean();
	}

	private static Resource[] getResources() {
		return new Resource[] {new ClassPathResource("application.properties")};
	}
}
