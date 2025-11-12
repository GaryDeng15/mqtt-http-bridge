package com.gary.mqtthttpbridge.controller;

import com.gary.mqtthttpbridge.model.Pack;
import com.gary.mqtthttpbridge.service.PackService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class TestController {
    @Resource
    private PackService packService;

    @GetMapping("/getPackTest")
    public Object getPackTest(){
        return packService.getLastPack();
    }
}
