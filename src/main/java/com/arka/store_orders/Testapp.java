package com.arka.store_orders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Testapp {
    @GetMapping
    public String testApp(){
        return "Hello from orders";
    }
}
