package com.gary.mqtthttpbridge.controller;

import com.gary.mqtthttpbridge.model.*;
import com.gary.mqtthttpbridge.service.BatteryOverallService;
import com.gary.mqtthttpbridge.service.BatteryService;
import com.gary.mqtthttpbridge.service.ContainerService;
import com.gary.mqtthttpbridge.service.PackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
@Tag(name = "数据孪生数据接口", description = "提供数据孪生所需数据")
public class GlobalDataController {
    @Resource
    private ContainerService containerService;

    @Resource
    private BatteryService batteryService;

    @Resource
    private PackService packService;

    @Resource
    private BatteryOverallService batteryOverallService;

    //----------------------[/getContainer]----------------------//
    @GetMapping("/getContainer")
    @Operation(
            summary = "获取设备容器状态", // 接口描述
            description = "返回设备容器的运行状态和故障信息，外层以Container为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取容器状态",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Container，value为容器状态对象",
                            allOf = {
                                            Object.class,
                                            //name = "Container", // Map的key
                                            //description = "设备容器状态详情",
                                            RestContainer.class // 关联RestContainer类的Schema

                            },
                            example = "{\"Container\": {\"ACOperatingStatus\": 1, \"PowerStatus\": 0, \"YLOperatingStatus\": 0, \"ACFault\": 1, \"PowerFault\": 1, \"YLOperatingFault\": 0}}"
                    )
            )
    )
    public Map<String, Object> getContainer(){
        /*RestContainer restContainer = new RestContainer(1,0,0,1,1,0);*/

        // 2. 用 Map 封装，key 为 "root"，value 为实体类对象
        Map<String, Object> result = new HashMap<>();
        result.put("Container", containerService.getLastContainer());
        return result;
    }


    //----------------------[/getBatteryOverall]----------------------//
    @GetMapping("/getBatteryOverall")
    @Operation(
            summary = "获取电池总体数据", // 接口描述
            description = "返回电池总体数据，外层以BatteryOverall为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取电池总体数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为BatteryOverall，value为电池总体数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestBatteryOverall.class // 关联RestContainer类的Schema
                            },
                            example = "{\"BatteryOverall\": {\"totalCurrent\": 12.5,\"totalVoltage\": 0,\"maximumBatteryVoltage\": 210.2,\"maximumVoltageBatteryPackNumber\": \"pack1\",\"maximumVoltageBatteryDOT\": \"battery-1-1\",\"minimumBatteryVoltage\": 8.74,\"lowestVoltageBatteryPackNumber\": \"pack3\",\"lowestVoltageBatteryDOT\": \"battery-2-12\",\"maximumBatteryTemperature\": 32.64,\"highestBatteryTemperaturePackPumber\": \"pack2\",\"maximumBatteryTemperatureBatteryDOT\": \"battery-3-1\",\"minimumBatteryTemperature\": 43.4,\"lowestTemperatureBatteryPackNumber\": \"pack7\",\"minimumTemperatureBatteryPointNumber\": \"battery-3-1\",\"cumulativeCharge\": 4321.42,\"cumulativeDischarge\": 2321.3,\"dischargeCapacity\": 1482.7,\"dischargeQuantityOnTheSameDay\": 74332.1,\"chargeTheSameDay\": 114332.4,\"currentStatus\": 3,\"real-timeChargingPowerOfBatteryStack\": 42.4,\"real-timeDischargingPowerOfBatteryStack\": 62.4}}"

                    )
            )
    )
    public Map<String, Object> getBatteryOverall(){
        /*RestBatteryOverall restBatteryOverall = new RestBatteryOverall(12.5,4.56,210.2,"pack1","battery-1-1",8.74,"pack3","battery-2-12",32.64,"pack2","battery-3-1",43.4,"pack7","battery-3-1",4321.42,2321.3,1482.7,74332.1,114332.4,3,42.4,62.4);*/

        Map<String, Object> result = new HashMap<>();

        result.put("BatteryOverall", batteryOverallService.getLastBatteryOverall());
        return result;
    }

    //----------------------[/getPackAll]----------------------//
    @GetMapping("/getPackAll")
    @Operation(
            summary = "获取电池pack数据", // 接口描述
            description = "返回8个pack的数据，外层以PackAll为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取电池pack数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为PackAll，value为总体电池pack数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackAll.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"PackAll\": {\n" +
                                    "    \"pack1\": {\n" +
                                    "      \"current\": 13.5,\n" +
                                    "      \"voltage\": 51.34,\n" +
                                    "      \"insulationResistance\": 21.444,\n" +
                                    "      \"averageMonomerResistance\": 56.38,\n" +
                                    "      \"averageMonomerTemperature\": 17.89,\n" +
                                    "      \"terminalTemperature\": 32.11,\n" +
                                    "      \"btteryPackReal-timeChargingPower\": 76.42,\n" +
                                    "      \"batteryPackReal-timeDischargingPower\": 1234.6\n" +
                                    "    },\n" +
                                    "    \"pack2\": {\n" +
                                    "      \"current\": 13.5,\n" +
                                    "      \"voltage\": 51.34,\n" +
                                    "      \"insulationResistance\": 21.444,\n" +
                                    "      \"averageMonomerResistance\": 56.38,\n" +
                                    "      \"averageMonomerTemperature\": 17.89,\n" +
                                    "      \"terminalTemperature\": 32.11,\n" +
                                    "      \"btteryPackReal-timeChargingPower\": 76.42,\n" +
                                    "      \"batteryPackReal-timeDischargingPower\": 1234.6\n" +
                                    "    },\n" +
                                    "    \"pack3\": {\n" +
                                    "      \"current\": 13.5,\n" +
                                    "      \"voltage\": 51.34,\n" +
                                    "      \"insulationResistance\": 21.444,\n" +
                                    "      \"averageMonomerResistance\": 56.38,\n" +
                                    "      \"averageMonomerTemperature\": 17.89,\n" +
                                    "      \"terminalTemperature\": 32.11,\n" +
                                    "      \"btteryPackReal-timeChargingPower\": 76.42,\n" +
                                    "      \"batteryPackReal-timeDischargingPower\": 1234.6\n" +
                                    "    },\n" +
                                    "    \"pack4\": {\n" +
                                    "      \"current\": 13.5,\n" +
                                    "      \"voltage\": 51.34,\n" +
                                    "      \"insulationResistance\": 21.444,\n" +
                                    "      \"averageMonomerResistance\": 56.38,\n" +
                                    "      \"averageMonomerTemperature\": 17.89,\n" +
                                    "      \"terminalTemperature\": 32.11,\n" +
                                    "      \"btteryPackReal-timeChargingPower\": 76.42,\n" +
                                    "      \"batteryPackReal-timeDischargingPower\": 1234.6\n" +
                                    "    },\n" +
                                    "    \"pack5\": {\n" +
                                    "      \"current\": 13.5,\n" +
                                    "      \"voltage\": 51.34,\n" +
                                    "      \"insulationResistance\": 21.444,\n" +
                                    "      \"averageMonomerResistance\": 56.38,\n" +
                                    "      \"averageMonomerTemperature\": 17.89,\n" +
                                    "      \"terminalTemperature\": 32.11,\n" +
                                    "      \"btteryPackReal-timeChargingPower\": 76.42,\n" +
                                    "      \"batteryPackReal-timeDischargingPower\": 1234.6\n" +
                                    "    },\n" +
                                    "    \"pack6\": {\n" +
                                    "      \"current\": 13.5,\n" +
                                    "      \"voltage\": 51.34,\n" +
                                    "      \"insulationResistance\": 21.444,\n" +
                                    "      \"averageMonomerResistance\": 56.38,\n" +
                                    "      \"averageMonomerTemperature\": 17.89,\n" +
                                    "      \"terminalTemperature\": 32.11,\n" +
                                    "      \"btteryPackReal-timeChargingPower\": 76.42,\n" +
                                    "      \"batteryPackReal-timeDischargingPower\": 1234.6\n" +
                                    "    },\n" +
                                    "    \"pack7\": {\n" +
                                    "      \"current\": 13.5,\n" +
                                    "      \"voltage\": 51.34,\n" +
                                    "      \"insulationResistance\": 21.444,\n" +
                                    "      \"averageMonomerResistance\": 56.38,\n" +
                                    "      \"averageMonomerTemperature\": 17.89,\n" +
                                    "      \"terminalTemperature\": 32.11,\n" +
                                    "      \"btteryPackReal-timeChargingPower\": 76.42,\n" +
                                    "      \"batteryPackReal-timeDischargingPower\": 1234.6\n" +
                                    "    },\n" +
                                    "    \"pack8\": {\n" +
                                    "      \"current\": 13.5,\n" +
                                    "      \"voltage\": 51.34,\n" +
                                    "      \"insulationResistance\": 21.444,\n" +
                                    "      \"averageMonomerResistance\": 56.38,\n" +
                                    "      \"averageMonomerTemperature\": 17.89,\n" +
                                    "      \"terminalTemperature\": 32.11,\n" +
                                    "      \"btteryPackReal-timeChargingPower\": 76.42,\n" +
                                    "      \"batteryPackReal-timeDischargingPower\": 1234.6\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPackAll(){
        /*Pack tempRestPack = new RestPack(
                13.5,
                51.34,
                21.444,
                56.38,
                17.89,
                32.11,
                76.42,
                1234.6
        );

        RestPackAll restPackAll = new RestPackAll(
                tempRestPack,
                tempRestPack,
                tempRestPack,
                tempRestPack,
                tempRestPack,
                tempRestPack,
                tempRestPack,
                tempRestPack
        );*/
        RestPackAll restPackAll = new RestPackAll();

        Map<String, Object> result = new HashMap<>();
        restPackAll = packService.getPackAll();
        result.put("PackAll", restPackAll);
        return result;
    }

    //----------------------[/getPack1BatteryData]----------------------//
    @GetMapping("/getPack1BatteryData")
    @Operation(
            summary = "获取Pack1电池数据", // 接口描述
            description = "返回Pack1的数据，外层以Pack1BatteryData为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取Pack1电池数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Pack1BatteryData，value为Pack电池数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackBatteryData.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"Pack1BatteryData\": {\n" +
                                    "    \"battery1\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery2\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery3\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery4\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery5\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery6\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery7\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery8\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery9\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery10\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery11\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery12\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery13\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery14\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPack1BatteryData(){
        /*RestBattery tempRestBatter = new RestBattery(12.5, 43.565, 833.31);

        RestPackBatteryData pack1BatteryData = new RestPackBatteryData(
                tempRestBatter, tempRestBatter, tempRestBatter, tempRestBatter,
                tempRestBatter, tempRestBatter, tempRestBatter, tempRestBatter,
                tempRestBatter, tempRestBatter, tempRestBatter, tempRestBatter,
                tempRestBatter, tempRestBatter);*/
        RestPackBatteryData restPackBatteryData = new RestPackBatteryData();
        restPackBatteryData = batteryService.getBatteryByPack(1);

        Map<String, Object> result = new HashMap<>();
        result.put("Pack1BatteryData", restPackBatteryData);
        return result;
    };

    //----------------------[/getPack2BatteryData]----------------------//
    @GetMapping("/getPack2BatteryData")
    @Operation(
            summary = "获取Pack2电池数据", // 接口描述
            description = "返回Pack2的数据，外层以Pack2BatteryData为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取Pack2电池数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Pack2BatteryData，value为Pack电池数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackBatteryData.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"Pack2BatteryData\": {\n" +
                                    "    \"battery1\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery2\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery3\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery4\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery5\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery6\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery7\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery8\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery9\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery10\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery11\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery12\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery13\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery14\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPack2BatteryData(){
/*        RestBattery tempRestBatter = new RestBattery(12.5, 43.565, 833.31);

        RestPackBatteryData Pack2BatteryData = new RestPackBatteryData(
                tempRestBatter, tempRestBatter, tempRestBatter, tempRestBatter,
                tempRestBatter, tempRestBatter, tempRestBatter, tempRestBatter,
                tempRestBatter, tempRestBatter, tempRestBatter, tempRestBatter,
                tempRestBatter, tempRestBatter);*/


        Map<String, Object> result = new HashMap<>();
        RestPackBatteryData Pack2BatteryData = new RestPackBatteryData();
        result.put("Pack2BatteryData", batteryService.getBatteryByPack(2));
        return result;
    };

    //----------------------[/getPack3BatteryData]----------------------//
    @GetMapping("/getPack3BatteryData")
    @Operation(
            summary = "获取Pack3电池数据", // 接口描述
            description = "返回Pack3的数据，外层以Pack3BatteryData为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取Pack3电池数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Pack3BatteryData，value为Pack电池数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackBatteryData.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"Pack3BatteryData\": {\n" +
                                    "    \"battery1\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery2\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery3\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery4\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery5\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery6\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery7\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery8\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery9\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery10\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery11\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery12\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery13\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery14\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPack3BatteryData(){

        Map<String, Object> result = new HashMap<>();

        result.put("Pack3BatteryData", batteryService.getBatteryByPack(3));
        return result;
    };

    //----------------------[/getPack4BatteryData]----------------------//
    @GetMapping("/getPack4BatteryData")
    @Operation(
            summary = "获取Pack4电池数据", // 接口描述
            description = "返回Pack4的数据，外层以Pack4BatteryData为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取Pack4电池数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Pack4BatteryData，value为Pack电池数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackBatteryData.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"Pack4BatteryData\": {\n" +
                                    "    \"battery1\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery2\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery3\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery4\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery5\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery6\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery7\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery8\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery9\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery10\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery11\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery12\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery13\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery14\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPack4BatteryData(){

        Map<String, Object> result = new HashMap<>();

        result.put("Pack4BatteryData", batteryService.getBatteryByPack(4));
        return result;
    };

    //----------------------[/getPack5BatteryData]----------------------//
    @GetMapping("/getPack5BatteryData")
    @Operation(
            summary = "获取Pack5电池数据", // 接口描述
            description = "返回Pack5的数据，外层以Pack5BatteryData为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取Pack5电池数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Pack5BatteryData，value为Pack电池数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackBatteryData.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"Pack5BatteryData\": {\n" +
                                    "    \"battery1\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery2\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery3\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery4\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery5\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery6\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery7\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery8\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery9\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery10\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery11\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery12\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery13\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery14\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPack5BatteryData(){

        Map<String, Object> result = new HashMap<>();
        result.put("Pack5BatteryData", batteryService.getBatteryByPack(5));
        return result;
    };

    //----------------------[/getPack6BatteryData]----------------------//
    @GetMapping("/getPack6BatteryData")
    @Operation(
            summary = "获取Pack6电池数据", // 接口描述
            description = "返回Pack6的数据，外层以Pack6BatteryData为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取Pack5电池数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Pack6BatteryData，value为Pack电池数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackBatteryData.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"Pack6BatteryData\": {\n" +
                                    "    \"battery1\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery2\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery3\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery4\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery5\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery6\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery7\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery8\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery9\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery10\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery11\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery12\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery13\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery14\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPack6BatteryData(){

        Map<String, Object> result = new HashMap<>();

        result.put("Pack6BatteryData", batteryService.getBatteryByPack(6));
        return result;
    };

    //----------------------[/getPack7BatteryData]----------------------//
    @GetMapping("/getPack7BatteryData")
    @Operation(
            summary = "获取Pack7电池数据", // 接口描述
            description = "返回Pack7的数据，外层以Pack7BatteryData为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取Pack7电池数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Pack7BatteryData，value为Pack电池数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackBatteryData.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"Pack7BatteryData\": {\n" +
                                    "    \"battery1\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery2\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery3\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery4\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery5\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery6\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery7\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery8\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery9\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery10\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery11\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery12\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery13\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery14\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPack7BatteryData(){

        Map<String, Object> result = new HashMap<>();
        RestPackBatteryData Pack7BatteryData = new RestPackBatteryData();
        result.put("Pack7BatteryData", batteryService.getBatteryByPack(7));
        return result;
    };

    //----------------------[/getPack8BatteryData]----------------------//
    @GetMapping("/getPack8BatteryData")
    @Operation(
            summary = "获取Pack8电池数据", // 接口描述
            description = "返回Pack8的数据，外层以Pack8BatteryData为key包裹"
    )
    @ApiResponse(
            responseCode = "200", // 响应状态码
            description = "成功获取Pack8电池数据",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            //type = "object", // 根节点是对象（Map）
                            description = "返回结果外层Map，key为Pack8BatteryData，value为Pack电池数据对象",
                            allOf = {
                                    Object.class,
                                    //name = "Container", // Map的key
                                    //description = "设备容器状态详情",
                                    RestPackBatteryData.class // 关联RestContainer类的Schema
                            },
                            example = "{\n" +
                                    "  \"Pack8BatteryData\": {\n" +
                                    "    \"battery1\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery2\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery3\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery4\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery5\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery6\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery7\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery8\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery9\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery10\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery11\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery12\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery13\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    },\n" +
                                    "    \"battery14\": {\n" +
                                    "      \"current\": 12.5,\n" +
                                    "      \"voltage\": 43.565,\n" +
                                    "      \"temperature\": 833.31\n" +
                                    "    }\n" +
                                    "  }\n" +
                                    "}"

                    )
            )
    )
    public Map<String, Object> getPack8BatteryData(){
        Map<String, Object> result = new HashMap<>();
        result.put("Pack8BatteryData", batteryService.getBatteryByPack(8));
        return result;
    };
}
