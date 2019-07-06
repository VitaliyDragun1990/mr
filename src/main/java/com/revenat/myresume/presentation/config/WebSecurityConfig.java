package com.revenat.myresume.presentation.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.revenat.myresume.presentation.security.service.CustomRememberMeService;

@Configuration
@ComponentScan({
	"com.revenat.myresume.presentation.security.service",
	"com.revenat.myresume.presentation.security.handler"
})
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
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
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/my-profile", "/profile/edit", "/profile/edit/**", "/profile/remove").hasAnyAuthority(Constants.USER)
			.anyRequest().permitAll()
		.and()
		.formLogin()
			.loginPage("/sign-in")
			.loginProcessingUrl("/sign-in-handler")
			.usernameParameter("uid")
			.passwordParameter("password")
			.defaultSuccessUrl("/my-profile")
			.failureUrl("/sign-in-failed")
		.and()
		.logout()
			.logoutUrl("/sign-out")
			.logoutSuccessUrl("/welcome")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
		.and()
		.rememberMe()
			.rememberMeParameter("remember-me")
			.rememberMeServices(persistentTokenRememberMeService)
		.and()
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		/*
		 * .and() .csrf() .disable()
		 */;
	}

}
