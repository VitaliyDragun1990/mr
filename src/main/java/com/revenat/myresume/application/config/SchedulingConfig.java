package com.revenat.myresume.application.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(applicationScheduler());
	}
	
	/**
	 * 
	 * By default, beans defined using Java config that have a public close or shutdown method are automatically enlisted with a destruction callback.
	 */
	@Bean(destroyMethod = "shutdown")
	public ScheduledExecutorService applicationScheduler() {
		return Executors.newScheduledThreadPool(1);
	}
}
