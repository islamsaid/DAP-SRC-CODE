package com.asset.dailyappnotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.asset")
public class DailyAppNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyAppNotificationServiceApplication.class, args);
	}

}
