package com.revenat.myresume.presentation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
	"com.revenat.myresume.presentation.image.service",
	"com.revenat.myresume.presentation.image.storage"
})
public class ImageProcessingConfig {

}
