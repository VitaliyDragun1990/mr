package com.revenat.myresume.application.config;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.transformer.TypeConverter;

/**
 * This custom implementation of {@link BeanDefinitionRegistryPostProcessor}
 * register custom {@link Converter} implementations annotated with
 * {@link TypeConverter} annotation using {@link ConversionServiceFactoryBean}
 * bean.
 * 
 * @author Vitaliy Dragun
 *
 */
public class ConverterRegistryPostProcessor
		implements BeanDefinitionRegistryPostProcessor, BeanPostProcessor,
		ApplicationContextAware/* , PriorityOrdered */{

	private static final String CONVERSION_SERVICE_NAME = "conversionService";

	private ApplicationContext appContext;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		// do nothing
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.appContext = applicationContext;

	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
		registry.registerBeanDefinition(CONVERSION_SERVICE_NAME,
				BeanDefinitionBuilder.rootBeanDefinition(ConversionServiceFactoryBean.class).getBeanDefinition());

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		if (CONVERSION_SERVICE_NAME.equals(beanName)) {
			Map<String, Converter> beansOfType = appContext.getBeansOfType(Converter.class);
			ConversionServiceFactoryBean conversionFactoryBean = (ConversionServiceFactoryBean) bean;
			Set<Converter> converters = new HashSet<>(beansOfType.values());
			conversionFactoryBean.setConverters(converters);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}

}
