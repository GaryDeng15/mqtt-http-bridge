package com.gary.mqtthttpbridge.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class GlobalDataController {
    @GetMapping("/getContainer")
    public String getContainer(){
        return "接口正在开发中.....";
    }
}
