package org.sav.fornas.iotws.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceCommand;
import org.sav.fornas.dto.iot.DeviceView;
import org.sav.fornas.iotws.config.DeviceSessionManager;
import org.sav.fornas.iotws.repository.DeviceRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceCommandConsumer {

	private final DeviceSessionManager sessionManager;
	private final ObjectMapper objectMapper;
	private final DeviceRepository deviceRepository;

	@KafkaListener(topics = "device-commands", groupId = "iot-ws")
	public void listen(DeviceCommand command) {
		log.debug(">>> Received command from Kafka: {}", command);
		DeviceView resp =  deviceRepository.findProjectedById(command.getDeviceId()).orElseThrow();

		sessionManager.sendToDevice(
				command.getDeviceId(),
				resp,
				objectMapper
		);
	}
}

