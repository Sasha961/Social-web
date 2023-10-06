package com.skillbox.socialnetwork.configs;

import com.skillbox.socialnetwork.services.Impl.UserServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig {
	private UserServiceImpl userService;

	@Bean
	public SecurityFilterChain filterChain(@NonNull HttpSecurity http) throws Exception {

		http    .headers().frameOptions().disable()
				.and()
				.addFilterBefore(new CorsFilter(), org.springframework.web.filter.CorsFilter.class)
				.addFilterAt(new CorsFilter(), org.springframework.web.filter.CorsFilter.class)
				.addFilterAfter(new CorsFilter(), org.springframework.web.filter.CorsFilter.class)
//				.csrf().disable()
//				.cors().disable()
				.authorizeRequests()
				.antMatchers("/setNewPassword").authenticated()
				.antMatchers("/password/recovery/{linkId}").hasRole("USER")
				.antMatchers("/login/**").permitAll()
				.anyRequest().permitAll()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//				.and().addFilter(new CorsFilter());
//				.and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Autowired
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

//	@Autowired
//	public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
//		this.jwtRequestFilter = jwtRequestFilter;
//	}


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(@NonNull AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}


	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userService);
		return daoAuthenticationProvider;
	}


}
