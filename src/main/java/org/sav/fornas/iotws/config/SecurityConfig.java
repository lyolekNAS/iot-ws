package org.sav.fornas.iotws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable()) // потрібне для WS
				.authorizeHttpRequests(auth -> auth
						.anyRequest().permitAll() // дозволяє всі запити
				)
				.httpBasic(basic -> basic.disable()) // відключає дефолтну Basic auth
				.formLogin(login -> login.disable()); // відключає login-форму

		return http.build();
	}
}
