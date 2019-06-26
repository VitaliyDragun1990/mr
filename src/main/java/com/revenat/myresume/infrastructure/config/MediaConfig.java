package com.revenat.myresume.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
		"com.revenat.myresume.infrastructure.repository.media.impl"
		)
public class MediaConfig {

}
