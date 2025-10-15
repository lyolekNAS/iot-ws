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
//	@Query("""
//        INSERT INTO DevicePortsHistory dph (dph.device.id, dph.gpio, dph.value, dph.ioType, dph.onTime)
//            VALUES  (:deviceId, :gpio, :value, 'out', CURRENT_TIMESTAMP)
//        """)
	@Query(value = """
    INSERT INTO device_ports_history (device_id, gpio, value, io_type, on_time)
        VALUES (:deviceId, :gpio, :value, 'out', CURRENT_TIMESTAMP)
    """, nativeQuery = true)
	void saveDeviceStateHistory(@Param("deviceId") Integer deviceId, @Param("gpio") String gpio, @Param("value") Double value);

	@Modifying
//	@Query("""
//        INSERT INTO DevicePortsHistory dph (dph.device.id, dph.gpio, dph.value, dph.ioType, dph.onTime)
//            SELECT dp.device.id, dp.gpio, dp.value, dp.ioType, CURRENT_TIMESTAMP
//                FROM DevicePorts dp
//                WHERE dp.device.id = :deviceId
//                    AND dp.ioType = 'in'
//        """)
	@Query(value = """
        INSERT INTO device_ports_history (device_id, gpio, value, io_type, on_time)
            SELECT dp.device_id, dp.gpio, dp.value, dp.io_type, CURRENT_TIMESTAMP
                FROM device_ports dp
                WHERE dp.device_id = :deviceId
                      AND dp.io_type = 'in'
    """, nativeQuery = true)
	void copyDeviceStateHistory(@Param("deviceId") Integer deviceId);

	@Modifying
	@Query("UPDATE Device d SET d.lastUpdated = CURRENT_TIMESTAMP WHERE d.id = :deviceId")
	void updateDeviceUpd(@Param("deviceId") Integer deviceId);
}
