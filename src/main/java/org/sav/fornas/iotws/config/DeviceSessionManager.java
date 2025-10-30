package org.sav.fornas.iotws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotws.entity.Device;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeviceSessionManager {

	private final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

	public void addSession(Device device, WebSocketSession session) {
		log.debug(">>> Session started for device {}", device.getId());
		sessions.put(device.getId(), session);
	}

	public void removeSession(Device device) {
		log.debug(">>> Session removed for device {}", device.getId());
		sessions.remove(device.getId());
	}

	public WebSocketSession getSession(int deviceId) {
		return sessions.get(deviceId);
	}

	public void sendToDevice(int deviceId, Object payload, ObjectMapper objectMapper) {
		WebSocketSession session = sessions.get(deviceId);
		if (session != null && session.isOpen()) {
			try {
				String json = objectMapper.writeValueAsString(payload);
				session.sendMessage(new TextMessage(json));
				log.debug(">>> Sent to device {}: {}", deviceId, json);
			} catch (Exception e) {
				log.error(">>> Failed to send message to device {}", deviceId, e);
			}
		} else {
			log.warn(">>> No open session for device {}", deviceId);
		}
	}
}

