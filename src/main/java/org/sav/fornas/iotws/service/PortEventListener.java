package org.sav.fornas.iotws.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotws.dto.iot.DeviceCommand;
import org.sav.fornas.iotws.dto.iot.DeviceView;
import org.sav.fornas.iotws.config.DeviceSessionManager;
import org.sav.fornas.iotws.repository.DeviceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class PortEventListener {

	private final DeviceSessionManager sessionManager;
	private final ObjectMapper objectMapper;
	private final DeviceRepository deviceRepository;

	@Bean
	public Consumer<DeviceCommand> portValueIn() {
		return event -> {
			log.debug(">>> Received event: {}", event);
			DeviceView resp =  deviceRepository.findProjectedById(event.getDeviceId()).orElseThrow();

			sessionManager.sendToDevice(
					event.getDeviceId(),
					resp,
					objectMapper
			);

		};
	}
}
