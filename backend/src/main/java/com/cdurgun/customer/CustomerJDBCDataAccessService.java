package com.cdurgun.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        String sql = """
                SELECT id, name, email, age 
                FROM customer
                """;
//        RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
//            Customer customer = new Customer(
//                    rs.getLong("id"),
//                    rs.getString("name"),
//                    rs.getString("email"),
//                    rs.getInt("age")
//            );
//            return customer;
//        };
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        String sql = """
                SELECT id, name, email, age 
                FROM customer
                WHERE id=?
                """;
//        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, customerRowMapper, id));
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age) 
                VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        System.out.println("jdbcTemplate.insert:" + result);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        String sql = """
                SELECT count(id) 
                FROM customer
                WHERE name=?
                """;
        Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return cnt != null && cnt > 0;

    }

    @Override
    public boolean existsCustomerWithId(Long id) {
        String sql = """
                SELECT count(id) 
                FROM customer
                WHERE email=?
                """;
        Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return cnt != null && cnt > 0;
    }

    @Override
    public void deleteCustomer(Customer customer) {
        var sql = """
                DELETE FROM customer 
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(
                sql,
                customer.getId()
        );
        System.out.println("jdbcTemplate.delete:" + result);
    }

    @Override
    public void updateCustomer(Customer customer) {
        var sql = """
                UPDATE customer SET 
                name = ?, 
                email = ?,
                age = ?
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge(),
                customer.getId()
        );
        System.out.println("jdbcTemplate.update:" + result);
    }
}
