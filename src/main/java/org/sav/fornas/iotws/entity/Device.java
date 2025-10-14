package org.sav.fornas.iotws.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;

	LocalDateTime lastUpdated;

	String username;
	String password;

	int placeId;

	@OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	@JsonManagedReference
	List<DevicePorts> devicePorts;
}
