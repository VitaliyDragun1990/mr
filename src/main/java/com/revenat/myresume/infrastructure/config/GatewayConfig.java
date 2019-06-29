package com.revenat.myresume.infrastructure.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkGateway;
import com.revenat.myresume.infrastructure.gateway.social.impl.FacebookGateway;
import com.revenat.myresume.infrastructure.media.converter.TranslitConverter;

@Configuration
@PropertySource({
	"classpath:/properties/email.properties",
	"classpath:/properties/social.properties",
})
@ComponentScan({
	"com.revenat.myresume.infrastructure.gateway.email.impl",
	"com.revenat.myresume.infrastructure.gateway.social.impl",
	"com.revenat.myresume.infrastructure.gateway.filesystem.impl"
})
public class GatewayConfig {
	
	@Bean
	@Autowired
	public JavaMailSender javaMailSender(ConfigurableEnvironment env) {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(env.getRequiredProperty("email.smtp.server"));
		if (env.containsProperty("email.smtp.username")) {
			javaMailSender.setUsername(env.resolveRequiredPlaceholders(env.getRequiredProperty("email.smtp.username")));
			javaMailSender.setPassword(env.resolveRequiredPlaceholders(env.getRequiredProperty("email.smtp.password")));
			javaMailSender.setPort(env.getRequiredProperty("email.smtp.port", Integer.class));
			javaMailSender.setDefaultEncoding("UTF-8");
			javaMailSender.setJavaMailProperties(javaMailProperties());
		}
		
		return javaMailSender;
	}
	
	@Bean
	@Autowired
	public SocialNetworkGateway socialNetworkGateway(ConfigurableEnvironment env, TranslitConverter translitConverter) {
		String host = env.getRequiredProperty("app.host");
		String redirectUrl = env.getRequiredProperty("social.redirectUri");
		String appId = env.getRequiredProperty("social.facebook.appId");
		String appSecret = env.resolveRequiredPlaceholders(env.getRequiredProperty("social.facebook.secret"));
		return new FacebookGateway(appId, appSecret, host, redirectUrl, translitConverter);
	}
	
	private Properties javaMailProperties() {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.debug", "true");
		return props;
	}
}
