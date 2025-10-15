package org.sav.fornas.iotws.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.dto.iot.DeviceDto;
import org.sav.fornas.dto.iot.DeviceView;
import org.sav.fornas.dto.iot.PortDto;
import org.sav.fornas.iotws.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

	private final DeviceRepository deviceRepository;

	@Transactional
	public DeviceView updateDeviceState(DeviceDto device){
		deviceRepository.updateDeviceUpd(device.getId());
		deviceRepository.copyDeviceStateHistory(device.getId());
		List<PortDto> ports = device.getPorts();
		if (ports != null) {
			for (PortDto port : ports) {
				if (port.getValue() != null) {
					deviceRepository.updateDeviceState(
							device.getId(),
							port.getGpio(),
							port.getValue()
					);
					deviceRepository.saveDeviceStateHistory(
							device.getId(),
							port.getGpio(),
							port.getValue()
					);
				}
			}
		}
		return deviceRepository.findProjectedById(device.getId()).orElseThrow();
	}
}
