package com.cdurgun.journey;

import com.cdurgun.auth.AuthenticationRequest;
import com.cdurgun.auth.AuthenticationResponse;
import com.cdurgun.customer.CustomerDTO;
import com.cdurgun.customer.CustomerRegistrationRequest;
import com.cdurgun.jwt.JWTUtil;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthenticationIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JWTUtil jwtUtil;

    private static final Random RANDOM = new Random();
    private static final String AUTHENTICATION_PATH = "/api/v1/auth";
    private static final String CUSTOMER_PATH = "/api/v1/customers";

    @Test
    void canLogin() {
        //create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.lastName() + "-" + UUID.randomUUID() + "amigoscode.com";
        int age = RANDOM.nextInt(1, 100);
        String gender=(RANDOM.nextInt(2) == 0) ? "M" : "F";

        String password = "password";
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
                name, email, password, age, gender
        );

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        webTestClient.post()
                .uri(AUTHENTICATION_PATH+"/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();

        //Send a post request
        webTestClient.post()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(customerRegistrationRequest),
                        CustomerRegistrationRequest.class
                )
                .exchange()
                .expectStatus()
                .isOk();

        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(
                        new ParameterizedTypeReference<AuthenticationResponse>() {
                        })
                .returnResult();
        String jwtToken = result.getResponseHeaders()
                .get(AUTHORIZATION).get(0);

        AuthenticationResponse authenticationResponse = result.getResponseBody();

        CustomerDTO customerDTO = authenticationResponse.customerDTO();

        assertThat(jwtUtil.isTokenValid(
                jwtToken,
                customerDTO.username())).isTrue();

        assertThat(customerDTO.email()).isEqualTo(email);
        assertThat(customerDTO.age()).isEqualTo(age);
        assertThat(customerDTO.name()).isEqualTo(name);
        assertThat(customerDTO.gender()).isEqualTo(gender);
        assertThat(customerDTO.username()).isEqualTo(email);
        assertThat(customerDTO.roles()).isEqualTo(List.of("ROLE_USER"));

    }
}
