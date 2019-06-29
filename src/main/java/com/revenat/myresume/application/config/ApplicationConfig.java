package com.revenat.myresume.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	ServiceConfig.class,
	CacheConfig.class,
	SchedulingConfig.class
})
public class ApplicationConfig {

}
