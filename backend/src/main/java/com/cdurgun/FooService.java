package com.cdurgun;

import org.springframework.stereotype.Service;

@Service
public class FooService {

    private final Main.Foo foo;

    public FooService(Main.Foo foo) {
        this.foo = foo;
        System.out.println(getFooName());
    }

    String getFooName() {
        return foo.name();
    }


}
