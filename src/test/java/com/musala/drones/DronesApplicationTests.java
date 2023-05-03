package com.musala.drones;

import com.musala.drones.dto.AddMedicationsRowRequestDto;
import com.musala.drones.dto.DroneDto;
import com.musala.drones.model.DroneModel;
import com.musala.drones.model.DroneState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DronesApplicationTests {

	@Autowired
	WebTestClient client;

	@Test
	void contextLoads() {
	}

	@Test
	void getDrones() {
		client.get().uri("/drones")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$").isNotEmpty()
				.jsonPath("$[0].serialNumber").isNotEmpty();

	}

	@Test
	void getExistingDrone() {
		String serial = "MD-001";
		client.get().uri("/drones/" + serial)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.serialNumber").isEqualTo(serial);

	}

	@Test
	void getNonExistingDrone() {
		String serial = "HOUSE-MD";
		client.get().uri("/drones/" + serial)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void registerCorrectDrone() {
		String serial = "1l2k31j";
		DroneDto droneDto = new DroneDto(serial, DroneModel.LIGHTWEIGHT, 400,
				55, DroneState.DELIVERING);
		client.post().uri("/drones")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(droneDto), DroneDto.class)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	void registerIncorrectDrone() {
		String serial = "1l2k31j";
		DroneDto droneDto = new DroneDto(serial, DroneModel.LIGHTWEIGHT, 999,
				55, DroneState.DELIVERING);
		client.post().uri("/drones")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(droneDto), DroneDto.class)
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	@DirtiesContext
	void loadDroneCorrectly() {
		String serial = "MD-001";
		List<AddMedicationsRowRequestDto> medications = List.of(
				new AddMedicationsRowRequestDto("ASPIRIN", 3),
				new AddMedicationsRowRequestDto("CD_1", 3)

		);

		client.post().uri("/drones/%s/load".formatted(serial))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(medications), AddMedicationsRowRequestDto.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.serialNumber").isEqualTo(serial)
				.jsonPath("$.state").isEqualTo("LOADED");
	}

	@Test
	void loadDroneIncorrectly() {
		String serial = "MD-001a";
		List<AddMedicationsRowRequestDto> medications = List.of(
				new AddMedicationsRowRequestDto("ASPIRIN", 20),
				new AddMedicationsRowRequestDto("CD_1", 5)

		);

		client.post().uri("/drones/%s/load".formatted(serial))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(medications), AddMedicationsRowRequestDto.class)
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void checkLoadedMedications() {
		client.get().uri("/drones/HV-112312/medications")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$").isNotEmpty()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].medication.code").isEqualTo("ASPIRIN")
                .jsonPath("$[0].quantity").isEqualTo(2);
	}

	@Test
	void checkAvailableDronesForLoading() {
		client.get().uri("/drones/available")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
				.jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].serialNumber").isNotEmpty();
	}

	@Test
	void checkDroneBatteryLevel() {
		client.get().uri("/drones/SM-001/battery")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.batteryCapacity").isEqualTo(25);
	}
}
