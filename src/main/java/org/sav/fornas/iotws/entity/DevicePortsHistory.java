package org.sav.fornas.iotws.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
public class DevicePortsHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	LocalDateTime onTime;
	String gpio;
	Double value;
	String ioType;

	@ManyToOne
	@JoinColumn(name = "device_id")
	@JsonBackReference
	Device device;
}
