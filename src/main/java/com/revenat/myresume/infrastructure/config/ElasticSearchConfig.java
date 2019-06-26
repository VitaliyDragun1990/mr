package com.revenat.myresume.infrastructure.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ComponentScan("com.revenat.myresume.infrastructure.service.impl")
@EnableElasticsearchRepositories("com.revenat.myresume.infrastructure.repository.search")
public class ElasticSearchConfig {
	
	// Separate elastic search server instance config
	@Value("${elasticsearch.host}")
	private String esHost;
	
	@Value("${elasticsearch.port}")
	private int esPort;
	
	@Value("${elasticsearch.clustername}")
	private String esClusterName;
	
	@Bean
	public Client client() throws UnknownHostException {
		Settings esSettings = Settings.settingsBuilder()
				.put("cluster.name", esClusterName)
		        .put("index.analysis.analyzer.englishAnalyzer.char_filter", "html_strip")
		        .put("index.analysis.analyzer.englishAnalyzer.filter", "snowball,standard,lowercase")
		        .put("index.analysis.analyzer.englishAnalyzer.tokenizer", "standard")
				.build();
		
		return TransportClient.builder()
				.settings(esSettings)
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress.getByName(esHost), esPort));
	}
	
	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
		return new ElasticsearchTemplate(client(), new CustomEntityMapper());
	}
	

	// Embedded elastic search server instance config


	/*
	@Value("${elasticsearch.home}")
	private String elasticSearchHome;
	
	@Bean
	public Node node() {
		return new NodeBuilder()
				.local(true)
				.settings(Settings.builder()
						.put("path.home", elasticSearchHome)
//						.put("index.max_inner_result_window", 250)
//				        .put("index.write.wait_for_active_shards", 1)
//				        .put("index.query.default_field", "paragraph")
//				        .put("index.number_of_shards", 3)
//				        .put("index.number_of_replicas", 2)
				        .put("index.analysis.analyzer.englishAnalyzer.char_filter", "html_strip")
				        .put("index.analysis.analyzer.englishAnalyzer.filter", "snowball,standard,lowercase")
				        .put("index.analysis.analyzer.englishAnalyzer.tokenizer", "standard")
						)
				.node();
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchTemplate(node().client(), new CustomEntityMapper());
	}
	*/
}
