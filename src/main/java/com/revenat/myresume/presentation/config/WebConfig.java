package com.revenat.myresume.presentation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
	"com.revenat.myresume.presentation.controller",
	"com.revenat.myresume.presentation.filter",
	"com.revenat.myresume.presentation.listener"
})
public class WebConfig {

}
