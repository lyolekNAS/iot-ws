package org.sav.fornas.iotws.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sav.fornas.iotws.dto.iot.DeviceDto;
import org.sav.fornas.iotws.dto.iot.DeviceView;
import org.sav.fornas.iotws.dto.iot.PortDto;
import org.sav.fornas.iotws.entity.Device;
import org.sav.fornas.iotws.entity.DevicePorts;
import org.sav.fornas.iotws.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
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
		
		updateSchedulerPortValue(device.getId());
		
		return deviceRepository.findProjectedById(device.getId()).orElseThrow();
	}

	@Transactional
	private void updateSchedulerPortValue(Integer deviceId) {
		Device device = deviceRepository.findById(deviceId).orElse(null);
		if (device == null) return;

		List<DevicePorts> ports = device.getDevicePorts();
		if (ports == null) return;

		LocalTime currentTime = LocalTime.now();

		for (DevicePorts port : ports) {
			if ("scheduler".equals(port.getGpio())) {
				double schedulerValue = 0.0;

				if (port.getSchedulers() != null) {
					for (var scheduler : port.getSchedulers()) {
						if (scheduler.isEnabled() &&
							!currentTime.isBefore(scheduler.getStartTime()) &&
							!currentTime.isAfter(scheduler.getEndTime())) {
							schedulerValue = 1.0;
							break;
						}
					}
				}

				port.setValue(schedulerValue);
				deviceRepository.save(device);
				break;
			}
		}
	}
}
