package com.example.demo.actuator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/test")
    public String testEndPoint(){
        return "Spring boot actuator";
    }
}
