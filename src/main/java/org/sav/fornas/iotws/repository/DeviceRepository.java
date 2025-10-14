package org.sav.fornas.iotws.repository;

import org.sav.fornas.iotws.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
	Optional<Device> findByUsername(String userName);

	@Modifying
	@Query("UPDATE DevicePorts dp SET dp.value = :value WHERE dp.device.id = :deviceId AND dp.gpio = :gpio AND dp.ioType = 'out'")
	void updateDeviceState(@Param("deviceId") Integer deviceId, @Param("gpio") String gpio, @Param("value") Double value);

	@Modifying
	@Query("UPDATE Device d SET d.lastUpdated = CURRENT_TIMESTAMP WHERE d.id = :deviceId")
	void updateDeviceUpd(@Param("deviceId") Integer deviceId);
}
