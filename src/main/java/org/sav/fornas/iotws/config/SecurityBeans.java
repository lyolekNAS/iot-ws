package org.sav.fornas.iotws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityBeans {

	@Bean
	public PasswordEncoder passwordEncoder() {
		String encodingId = "argon2_v2";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		encoders.put("argon2_v2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder(
				"",
				32,
				18500,
				Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256)
		);

		return new DelegatingPasswordEncoder(encodingId, encoders);
	}
}

