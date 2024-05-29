package com.cdurgun.customer;

import java.util.List;

public record CustomerDTO(
        Long id,
        String name,
        String email,
        String gender,
        Integer age,
        List<String> roles,
        String username
) {

}
