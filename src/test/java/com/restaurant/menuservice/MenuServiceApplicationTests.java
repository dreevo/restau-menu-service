package com.restaurant.menuservice;

import com.restaurant.menuservice.domain.Food;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class MenuServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;


	@Test
	void whenGetRequestWithRefThenFoodReturned() {
		var foodRef = "4546745440";
		var foodToCreate = Food.of(foodRef, "desc", 5.5);
		Food expectedFood = webTestClient
				.post()
				.uri("/foods")
				.bodyValue(foodToCreate)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Food.class).value(food -> Assertions.assertThat(food).isNotNull())
				.returnResult().getResponseBody();

		webTestClient
				.get()
				.uri("/foods/" + foodRef)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Food.class).value(actualFood -> {
					Assertions.assertThat(actualFood).isNotNull();
					Assertions.assertThat(actualFood.ref()).isEqualTo(expectedFood.ref());
				});
	}

	@Test
	void whenPostRequestThenFoodCreated() {
		var expectedFood = Food.of("4546745430", "desc", 5.5);
		webTestClient.post().uri("/foods").bodyValue(expectedFood)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Food.class).value(result -> {
					Assertions.assertThat(result).isNotNull();
					Assertions.assertThat(result.ref()).isEqualTo(expectedFood.ref());
				});
	}


	@Test
	void whenPutRequestThenFoodUpdated() {
		var foodRef = "4546745420";
		var foodToCreate = Food.of(foodRef, "desc", 5.5);
		Food createdFood = webTestClient
				.post()
				.uri("/foods")
				.bodyValue(foodToCreate)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Food.class).value(food -> Assertions.assertThat(food).isNotNull())
				.returnResult().getResponseBody();
		var foodToUpdate = new Food(createdFood.id(), createdFood.ref(), createdFood.description(), 7.5,createdFood.chef(),
				createdFood.version(), createdFood.createdDate(), createdFood.lastModifiedDate());

		webTestClient
				.put()
				.uri("/foods/" + foodRef)
				.bodyValue(foodToUpdate)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Food.class).value(actualFood -> {
					Assertions.assertThat(actualFood).isNotNull();
					Assertions.assertThat(actualFood.price()).isEqualTo(foodToUpdate.price());
				});

	}

	@Test
	void whenDeleteRequestThenFoodDeleted() {
		var foodRef = "4546745410";
		var foodToCreate = Food.of(foodRef, "desc", 5.5);
		webTestClient
				.post()
				.uri("/foods")
				.bodyValue(foodToCreate)
				.exchange()
				.expectStatus().isCreated();

		webTestClient
				.delete()
				.uri("/foods/" + foodRef)
				.exchange()
				.expectStatus().isNoContent();

		webTestClient
				.get()
				.uri("/foods/" + foodRef)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(String.class).value(errorMessage ->
						Assertions.assertThat(errorMessage).isEqualTo("The food with ref " + foodRef + " was not found.")
				);
	}


}