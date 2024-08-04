package com.restaurant.menuservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.restaurant.menuservice.domain.Food;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MenuServiceApplicationTests {


	@Autowired
	private WebTestClient webTestClient;


	@Test
	void whenPostRequestThenFoodCreated() {
		var expectedFood = Food.of("4546745430", "desc", 5.5);
		webTestClient.post().uri("/foods")
				.bodyValue(expectedFood)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Food.class).value(result -> {
					Assertions.assertThat(result).isNotNull();
					Assertions.assertThat(result.ref()).isEqualTo(expectedFood.ref());
				});
	}

}
