package com.cdurgun.auth;

import com.cdurgun.customer.CustomerDTO;

public record AuthenticationResponse (
        String token,
        CustomerDTO customerDTO
) {
}
