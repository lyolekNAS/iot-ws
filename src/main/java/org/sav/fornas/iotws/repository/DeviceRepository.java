package org.sav.fornas.iotws.repository;

import org.sav.fornas.iotws.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
	Optional<Device> findByUsername(String userName);
}
