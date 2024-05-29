package com.cdurgun.customer;

import com.cdurgun.exception.DuplicateResourceException;
import com.cdurgun.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerDTOMapper customerDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao, CustomerDTOMapper customerDTOMapper, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDTO> getAllCustomers(){
        return customerDao.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomer(Long id) {
        return customerDao.selectCustomerById(id)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
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
                passwordEncoder.encode(customerRegistrationRequest.password()),
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
        Customer customer = customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)
                ));
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
