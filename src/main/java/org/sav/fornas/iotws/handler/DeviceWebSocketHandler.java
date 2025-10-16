package org.sav.fornas.iotws.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceDto;
import org.sav.fornas.iotws.entity.Device;
import org.sav.fornas.iotws.service.DeviceService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
public class DeviceWebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final DeviceService deviceService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Device device = (Device) session.getAttributes().get("device");
		log.debug("Device connected: {}", device.getId());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		try {
			Device device = (Device) session.getAttributes().get("device");
			log.debug(">>> Message from {}: {}", device.getId(), message.getPayload());
			DeviceDto dto = objectMapper.readValue(message.getPayload(), DeviceDto.class);
			dto.setId(device.getId());
			log.debug(">>> DTO from {}: {}", device.getId(), dto);

			String resp = objectMapper.writeValueAsString(deviceService.updateDeviceState(dto));


			log.debug(">>> from {} updated {}", device.getId(), resp);

			session.sendMessage(new TextMessage(resp));
		} catch (Exception e){
			log.info("Error:", e);
		}
	}
}

