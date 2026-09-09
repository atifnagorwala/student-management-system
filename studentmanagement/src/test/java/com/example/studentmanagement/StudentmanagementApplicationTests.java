package com.example.studentmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"AI_API_KEY=test-key"
})
class StudentmanagementApplicationTests {

	@Test
	void contextLoads() {
	}

}