package org.sav.fornas.iotws.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceDto;
import org.sav.fornas.iotws.entity.Device;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
public class DeviceWebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;

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
			log.debug(">>> DTO from {}: {}", device.getId(), dto);

//		session.sendMessage(new TextMessage("Received: " + message.getPayload()));
		} catch (Exception e){
			log.info("Error:", e);
		}
	}
}

