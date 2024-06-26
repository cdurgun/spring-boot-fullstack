package com.cdurgun.customer;

import com.cdurgun.AbstractTestcontainers;
import com.cdurgun.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {
        String email = FAKER.internet().safeEmailAddress()+ "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                "M"
        );

        underTest.save(customer);

        var actual = underTest.existsCustomerByEmail(email);

        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerById() {
        String email = FAKER.internet().safeEmailAddress()+ "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                "M"
        );

        underTest.save(customer);

        Long id = underTest.findAll().
                stream().
                filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var actual = underTest.existsCustomerById(id);

        assertThat(actual).isTrue();
       
    }
}