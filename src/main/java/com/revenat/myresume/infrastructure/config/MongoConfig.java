package com.revenat.myresume.infrastructure.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@PropertySource("classpath:properties/mongo.properties")
@EnableMongoRepositories({
	"com.revenat.myresume.infrastructure.repository.storage",
	"com.revenat.myresume.presentation.security.token.repository"
})
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoConfiguration {

	@Autowired
	private Environment env;
	
	@Override
	protected String getDatabaseName() {
		return env.getRequiredProperty("mongo.db");
	}
	
	@Bean(destroyMethod = "close")
	public MongoClient mongo() {
		return new MongoClient(env.getRequiredProperty("mongo.host"), env.getRequiredProperty("mongo.port", Integer.class));
	}
	
	@Override
	protected Collection<String> getMappingBasePackages() {
		return Arrays.asList(
				"com.revenat.myresume.domain.document",
				"com.revenat.myresume.presentation.security.token.model");
	}

}
