package com.cdurgun;

import com.cdurgun.customer.Customer;
import com.cdurgun.customer.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


import java.util.Random;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        //printBeans(applicationContext);

    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {

        return args -> {
            var faker = Faker.instance();
            Random random = new Random();
            var name=faker.name();
            var firstName = name.firstName().toLowerCase();
            var lastName = name.lastName().toLowerCase();
            String email = firstName  +"."+ lastName
                            +"@amigoscode.com";

            Customer customer = new Customer(
                    firstName +" "+lastName,
                    email,
                    random.nextInt(16, 99));

            customerRepository.save(customer);
        };
    }

    private static void printBeans(ConfigurableApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    @Bean
    public Foo getFoo() {
        return new Foo("bar");
    }

    record Foo(String name) {}

}

