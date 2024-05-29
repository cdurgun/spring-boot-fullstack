package com.cdurgun.auth;

public record AuthenticationRequest (
        String username,
        String password
) {

}
