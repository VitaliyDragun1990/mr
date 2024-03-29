package com.revenat.myresume.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	ElasticSearchConfig.class,
	GatewayConfig.class,
	JPAConfig.class,
	MediaConfig.class
})
public class InfrastructureConfig {

}
