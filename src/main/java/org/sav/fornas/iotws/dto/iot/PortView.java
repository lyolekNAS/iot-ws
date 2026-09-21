package org.sav.fornas.iotws.dto.iot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public interface PortView {
	int getId();
	String getName();
	String getGpio();
	Double getValue();
	String getIoType();
	@JsonIgnore
	List<PortSchedulerView> getSchedulers();
}