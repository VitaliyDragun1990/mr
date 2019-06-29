package com.revenat.myresume.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
	"classpath:/properties/media.properties",
})
@ComponentScan({
	"com.revenat.myresume.infrastructure.media.converter.impl",
	"com.revenat.myresume.infrastructure.media.optimizer.impl",
	"com.revenat.myresume.infrastructure.media.resizer.impl",
	"com.revenat.myresume.infrastructure.repository.media.impl"
})
public class MediaConfig {

}
