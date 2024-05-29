package com.cdurgun.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Long id);
    Optional<Customer> findCustomerByEmail(String email);

}
