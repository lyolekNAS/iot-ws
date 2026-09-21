package org.sav.fornas.iotws.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Setter
@Getter
@ToString
public class DevicePorts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;
	String gpio;
	Double value;
	String ioType;

	@ManyToOne
	@JoinColumn(name = "device_id")
	@JsonBackReference
	Device device;

	@OneToMany(mappedBy = "port", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@ToString.Exclude
	@JsonIgnore
	List<DevicePortsScheduler> schedulers;
}
