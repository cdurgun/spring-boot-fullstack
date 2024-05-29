package com.cdurgun.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{

    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(1L, "Ayse", "alex@gmail.com", "password", 21, "F");
        customers.add(alex);
        Customer cemal = new Customer(2L, "Cemal", "cemal@gmail.com", "password", 30, "M");
        customers.add(cemal);


    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();


    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream().anyMatch(c->c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerWithId(Long id) {
        return false;
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        return customers.stream()
                .filter(c->c.getUsername().equals(email))
                .findFirst();
    }
}
