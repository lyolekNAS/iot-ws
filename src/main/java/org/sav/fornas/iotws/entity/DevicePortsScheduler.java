package org.sav.fornas.iotws.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Entity
@Setter
@Getter
@ToString
public class DevicePortsScheduler {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(nullable = false)
	LocalTime startTime;

	@Column(nullable = false)
	LocalTime endTime;

	@Column(nullable = false)
	boolean enabled;

	@ManyToOne
	@JoinColumn(name = "port_id")
	@JsonBackReference
	DevicePorts port;
}
