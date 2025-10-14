package org.sav.fornas.iotws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class IotWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotWsApplication.class, args);
		log.info("-----------------------------<AppStarted>------------------------------");
	}

}
