package org.sav.fornas.iotws.dto.iot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortDto {
	int id;
	String name;
	String gpio;
	Double value;
	String ioType;
}
