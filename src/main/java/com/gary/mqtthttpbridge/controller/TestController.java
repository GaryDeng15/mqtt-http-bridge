package com.gary.mqtthttpbridge.controller;

import com.gary.mqtthttpbridge.model.Battery;
import com.gary.mqtthttpbridge.model.BatteryOverall;
import com.gary.mqtthttpbridge.model.Container;
import com.gary.mqtthttpbridge.model.Pack;
import com.gary.mqtthttpbridge.service.BatteryOverallService;
import com.gary.mqtthttpbridge.service.BatteryService;
import com.gary.mqtthttpbridge.service.ContainerService;
import com.gary.mqtthttpbridge.service.PackService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class TestController {

    @Resource
    private ContainerService containerService;

    @Resource
    private BatteryService batteryService;

    @Resource
    private PackService packService;

    @Resource
    private BatteryOverallService batteryOverallService;

   @GetMapping("/getLastContainer")
    public Object getLastContainer(){
        Map<String, Object> result = new HashMap<>();
        result.put("Container", containerService.getLastContainer());
        return result;
    }

    @PostMapping("/putOneContainer")
    public int putOneContainer(@RequestBody Container container){
        Container newContainer = new Container(0, 1, 0, 0, 0, 0);
        return containerService.insertOne(newContainer);
    }

    @GetMapping("/getLastBattery")
    public Object getLastBattery(){
       return batteryService.getLastBattery();
    }

    @PostMapping("/postOneBattery")
    public Object postOneBattery(@RequestBody Battery battery){
        Battery newBattery = new Battery(
                333.33,
                666.66,
                99.9,
                null,
                "test"
        );
        return batteryService.postOneBattery(newBattery);
    }

    @GetMapping("/getLastPack")
    public Object getLastPack(){
        return packService.getLastPack();
    }

    @PostMapping("/postOnePack")
    public Integer postOnePack(){
       Pack newPack = new Pack(
               "pack7",
               3.3,
               3.3,
               3.3,
               3.3,
               3.3,
               3.3,
               3.3,
               3.3,
               null);
       return packService.postOnePack(newPack);
    }

    @GetMapping("/getLastBatteryOverall")
    public Object getLastBatteryOverall(){
       return batteryOverallService.getLastBatteryOverall();
    }

    @PostMapping("/postOneBatOverall")
    public Integer postOneBatOverall(@RequestBody BatteryOverall batteryOverall){
       BatteryOverall newBatOverall = new BatteryOverall(
               4.4,
               4.4,
               4.4,
               "pack4",
               "battery-1-1",
               4.4,
               "pack4",
               "battery-1-1",
               4.4,
               "pack4",
               "battery-1-1",
               4.4,
               "pack4",
               "battery-1-1",
               4.4,
               4.4,
               4.4,
               4.4,
               4.4,
               4,
               4.4,
               4.4,
               null
       );
       return batteryOverallService.postOneBatOverall(newBatOverall);
    }
}
