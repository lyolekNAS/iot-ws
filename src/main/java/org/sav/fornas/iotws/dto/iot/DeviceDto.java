package org.sav.fornas.iotws.dto.iot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
	int id;
	String name;
	LocalDateTime lastUpdated;
	String username;
	List<PortDto> ports;
}
