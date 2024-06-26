package com.cdurgun.customer;

import com.cdurgun.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                "password", 20,
                "M"
        );

        underTest.insertCustomer(customer);

        List<Customer> actual = underTest.selectAllCustomers();

        assertThat(actual).isNotEmpty();




    }

    @Test
    void selectCustomerById() {
        String email = FAKER.internet().safeEmailAddress()+ "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                "M"
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers().
                stream().
                filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getGender()).isEqualTo(customer.getGender());

        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {

        Long id = -1L;

        var actual = underTest.selectCustomerById(id);

        assertThat(actual).isEmpty();

    }

    @Test
    void insertCustomer() {
        String email = FAKER.internet().safeEmailAddress()+ "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                "M"
        );

        underTest.insertCustomer(customer);

        Optional<Customer> actual = underTest.selectAllCustomers().
                stream().
                filter(c -> c.getEmail().equals(email))
                .findFirst();

        assertThat(actual).isNotEmpty();


    }

    @Test
    void existsPersonWithEmail() {
    }

    @Test
    void existsPersonWithId() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void updateCustomer() {
    }
}