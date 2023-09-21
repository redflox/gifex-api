package com.gifex.gifexapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFirstController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello world! not secured";
    }

    @GetMapping("/hellosecured")
    public String helloSecured(){
        return "Hello secured";
    }


}
