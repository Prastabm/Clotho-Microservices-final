package com.clotho_microservices.notification_service;

import com.clotho_microservices.notification_service.config.TestMailConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(TestMailConfig.class)
@ActiveProfiles("test")
class NotificationServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
