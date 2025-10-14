package org.sav.fornas.iotws.config;

import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotws.repository.DeviceRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Base64;
import java.util.Map;

@Component
@Slf4j
public class DeviceAuthInterceptor implements HandshakeInterceptor {

	private final DeviceRepository deviceRepository;
	private final PasswordEncoder passwordEncoder;

	public DeviceAuthInterceptor(DeviceRepository repo, PasswordEncoder passwordEncoder) {
		this.deviceRepository = repo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean beforeHandshake(
			ServerHttpRequest request,
			ServerHttpResponse response,
			WebSocketHandler wsHandler,
			Map<String, Object> attributes) {

		log.debug(">>> beforeHandshake");

		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Basic ")) return false;

		String decoded = new String(Base64.getDecoder().decode(authHeader.substring(6)));
		String[] parts = decoded.split(":", 2);
		if (parts.length != 2) return false;

		String userName = parts[0];
		String secret = parts[1];
		log.debug(">>> userName={}", userName);

		return deviceRepository.findByUsername(userName)
				.map(device -> {
					if (passwordEncoder.matches(secret, device.getPassword())) {
						log.debug(">>> password matches");
						attributes.put("device", device);
						return true;
					}

					log.debug(">>> password missmatch");
					return false;
				})
				.orElse(false);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
							   WebSocketHandler wsHandler, Exception exception) {
		log.debug("afterHandshake() request:{}, response:{}, wsHandler={}", request, response, wsHandler);
	}


}

