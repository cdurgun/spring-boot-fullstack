package com.cdurgun.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Long id);
    void insertCustomer(Customer customer);
    boolean existsCustomerWithEmail(String email);
    boolean existsCustomerWithId(Long id);
    void deleteCustomer(Customer customer);
    void updateCustomer(Customer customer);
    Optional<Customer> selectUserByEmail(String email);

}
