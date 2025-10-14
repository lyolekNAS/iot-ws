package org.sav.fornas.iotws.service;

import lombok.RequiredArgsConstructor;
import org.sav.fornas.dto.iot.DeviceDto;
import org.sav.fornas.dto.iot.PortDto;
import org.sav.fornas.iotws.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

	private final DeviceRepository deviceRepository;

	@Transactional
	public boolean updateDeviceState(DeviceDto device){
		deviceRepository.updateDeviceUpd(device.getId());
		List<PortDto> ports = device.getPorts();
		if (ports != null) {
			for (PortDto port : ports) {
				if (port.getValue() != null) {
					deviceRepository.updateDeviceState(
							device.getId(),
							port.getGpio(),
							port.getValue()
					);
				}
			}
		}

		return true;
	}
}
