package com.gary.mqtthttpbridge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class InitController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}
