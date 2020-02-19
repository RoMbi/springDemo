package com.mySpring.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Demo controller!";
    }

    @RequestMapping("/status204")
    ResponseEntity<String> noContent() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "This is empty");

        return new ResponseEntity<>(
                "Custom header set", headers, HttpStatus.NO_CONTENT);
    }
}