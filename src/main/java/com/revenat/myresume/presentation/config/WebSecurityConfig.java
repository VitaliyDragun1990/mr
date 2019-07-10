package com.revenat.myresume.presentation.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.revenat.myresume.presentation.security.service.CustomRememberMeService;

@Configuration
@ComponentScan({ "com.revenat.myresume.presentation.security.service",
		"com.revenat.myresume.presentation.security.handler" })
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	@Qualifier("applicationAccessDeniedHandler")
	private AccessDeniedHandler accessDeniedHandler;
	@Autowired
	private CustomRememberMeService persistentTokenRememberMeService;

	@Bean
	@Autowired
	public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
		JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
		persistentTokenRepository.setDataSource(dataSource);
		return persistentTokenRepository;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		registerCharacterEncodingFilter(http);

		http.authorizeRequests().antMatchers("/my-profile", "/profile/edit", "/profile/edit/**", "/profile/remove")
				.hasAnyAuthority(Constants.USER).anyRequest().permitAll().and().formLogin().loginPage("/sign-in")
				.loginProcessingUrl("/sign-in-handler").usernameParameter("uid").passwordParameter("password")
				.defaultSuccessUrl("/my-profile").failureUrl("/sign-in-failed").and().logout().logoutUrl("/sign-out")
				.logoutSuccessUrl("/welcome").invalidateHttpSession(true).deleteCookies("JSESSIONID").and().rememberMe()
				.rememberMeParameter("remember-me").rememberMeServices(persistentTokenRememberMeService).and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	/**
	 * We should register {@link CharacterEncodingFilter} first in chain to properly
	 * handle form submission with form fields filled with non-latin1 characters.
	 * 
	 */
	private void registerCharacterEncodingFilter(HttpSecurity http) {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);
		http.addFilterBefore(encodingFilter, CsrfFilter.class);
	}

}
