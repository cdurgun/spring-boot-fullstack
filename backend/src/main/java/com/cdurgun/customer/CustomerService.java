package com.cdurgun.customer;

import com.cdurgun.exception.DuplicateResourceException;
import com.cdurgun.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Long id) {
        return customerDao.selectCustomerById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();
        if (customerDao.existsCustomerWithEmail(email)) {
            throw new DuplicateResourceException("email already taken");
        }
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age(),
                customerRegistrationRequest.gender()
        );
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Long customerId) {
        Customer customer = customerDao.selectCustomerById(
                customerId).orElseThrow(() -> new ResourceNotFoundException("customer with id [%s] not found".formatted(customerId)) );
        customerDao.deleteCustomer(customer);
    }

    public void updateCustomer(Long customerId, CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = getCustomer(customerId);
        if (customerRegistrationRequest.name() != null && !customerRegistrationRequest.name().isBlank())
            customer.setName(customerRegistrationRequest.name());
        if (customerRegistrationRequest.email() != null && !customerRegistrationRequest.email().isBlank())
            customer.setEmail(customerRegistrationRequest.email());
        if (customerRegistrationRequest.age()!=null)
            customer.setAge(customerRegistrationRequest.age());
        if (customerRegistrationRequest.gender()!=null)
            customer.setGender(customerRegistrationRequest.gender());
        customerDao.updateCustomer(customer);
    }
}
