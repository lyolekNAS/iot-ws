package org.sav.fornas.iotws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.sav.fornas.iotws.handler.DeviceWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

	private final DeviceAuthInterceptor authInterceptor;
	private final ObjectMapper objectMapper;


	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(deviceHandler(), "/ws/device")
				.addInterceptors(authInterceptor)
				.setAllowedOrigins("*"); // або конкретні домени
	}

	@Bean
	public WebSocketHandler deviceHandler() {
		return new DeviceWebSocketHandler(objectMapper);
	}
}

