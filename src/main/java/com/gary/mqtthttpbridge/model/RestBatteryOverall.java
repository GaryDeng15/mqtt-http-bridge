package com.gary.mqtthttpbridge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestBatteryOverall {
    /**
     *  "总电流": "float",
     *  "totalCurrent": 12.5
     */
    @JsonProperty("totalCurrent")
    @Schema(name = "总电流", description = "Double", format = "double", example = "12.5")
    private Double totalCurrent;

    /**
     *  "总电压": "float",
     *  "totalVoltage": 0.0,
     */
    @JsonProperty("totalVoltage")
    @Schema(name = "总电压", description = "Double", format = "double", example = "543.0")
    private Double totalVoltage;

    /**
     * "最高电池电压": "float",
     * "maximumBatteryVoltage": 210.2
     */
    @JsonProperty("maximumBatteryVoltage")
    @Schema(name = "最高电池电压", description = "Double", format = "double", example = "210.2")
    private Double maximumBatteryVoltage;

    /**
     *  "最高电压电池组号": "string",
     *  "maximumVoltageBatteryPackNumber": "pack1"
     */
    @JsonProperty("maximumVoltageBatteryPackNumber")
    @Schema(name = "最高电压电池组号", description = "string", format = "string", example = "pack1")
    private String maximumVoltageBatteryPackNumber;

    /**
     *  "最高电压电池点号": "string",
     *  "maximumVoltageBatteryDOT": "battery-1-1",
     */
    @JsonProperty("maximumVoltageBatteryDOT")
    @Schema(name = "最高电压电池点号", description = "string", format = "string", example = "battery-1-1")
    private String maximumVoltageBatteryDOT;

    /**
     *  "最低电池电压": "float",
     *  "minimumBatteryVoltage": 201.0,
     */
    @JsonProperty("minimumBatteryVoltage")
    @Schema(name = "最低电池电压", description = "double", format = "double", example = "201.0")
    private Double minimumBatteryVoltage;

    /**
     *  "最低电压电池组号": "string",
     *  "lowestVoltageBatteryPackNumber": "pack1",
     */
    @JsonProperty("lowestVoltageBatteryPackNumber")
    @Schema(name = "最低电压电池组号", description = "string", format = "string", example = "pack1")
    private String lowestVoltageBatteryPackNumber;

    /**
     *  "最低电压电池点号": "string",
     *  "lowestVoltageBatteryDOT": "battery-2-12",
     */
    @JsonProperty("lowestVoltageBatteryDOT")
    @Schema(name = "最低电压电池点号", description = "string", format = "string", example = "battery-2-12")
    private String lowestVoltageBatteryDOT;

    /**
     *  "最高电池温度": "float",
     *  "maximumBatteryTemperature": 32,
     */
    @JsonProperty("maximumBatteryTemperature")
    @Schema(name = "最高电池温度", description = "double", format = "double", example = "32.0")
    private Double maximumBatteryTemperature;

    /**
     *  "最高电池温度组号": "string",
     *  "highestBatteryTemperaturePackPumber": "pack2",
     */
    @JsonProperty("highestBatteryTemperaturePackPumber")
    @Schema(name = "最高电池温度组号", description = "string", format = "string", example = "pack2")
    private String highestBatteryTemperaturePackPumber;

    /**
     *  "最高电池温度点号": "string",
     *  "maximumBatteryTemperatureBatteryDOT": "XXXX"
     */
    @JsonProperty("maximumBatteryTemperatureBatteryDOT")
    @Schema(name = "最高电池温度点号", description = "string", format = "string", example = "battery-3-1")
    private String maximumBatteryTemperatureBatteryDOT;

    /**
     *  "最低电池温度": "float",
     *  "minimumBatteryTemperature": 0.0
     */
    @JsonProperty("minimumBatteryTemperature")
    @Schema(name = "最低电池温度", description = "double", format = "double", example = "0.0")
    private Double minimumBatteryTemperature;

    /**
     *   "最低温度电池组号": "string",
     *   "lowestTemperatureBatteryPackNumber": "XXXX"
     */
    @JsonProperty("lowestTemperatureBatteryPackNumber")
    @Schema(name = "最低温度电池组号", description = "string", format = "string", example = "pack7")
    private String lowestTemperatureBatteryPackNumber;

    /**
     *   "最低温度电池点号": "string",
     *   "minimumTemperatureBatteryPointNumber": "XXXXX",
     */
    @JsonProperty("minimumTemperatureBatteryPointNumber")
    @Schema(name = "最低温度电池点号", description = "string", format = "string", example = "battery-3-1")
    private String minimumTemperatureBatteryPointNumber;

    /**
     *  "累计充电电量": "float",
     *  "cumulativeCharge": 0.0
     */
    @JsonProperty("cumulativeCharge")
    @Schema(name = "累计充电电量", description = "double", format = "double", example = "4321.42")
    private Double cumulativeCharge;

    /**
     *   "累计放电电量": "float",
     *   "cumulativeDischarge": 0.0
     */
    @JsonProperty("cumulativeDischarge")
    @Schema(name = "累计放电电量", description = "double", format = "double", example = "2321.3")
    private Double cumulativeDischarge;

    /**
     *   "可放电电量": "float",
     *   "dischargeCapacity": 0.0,
     */
    @JsonProperty("dischargeCapacity")
    @Schema(name = "可放电电量", description = "double", format = "double", example = "1482.7")
    private Double dischargeCapacity;

    /**
     *    "当天放电电量": "float",
     *    "dischargeQuantityOnTheSameDay": 0.0,
     */
    @JsonProperty("dischargeQuantityOnTheSameDay")
    @Schema(name = "当天放电电量", description = "double", format = "double", example = "74332.1")
    private Double dischargeQuantityOnTheSameDay;

    /**
     *    "当天充电电量": "float",
     *    "chargeTheSameDay": 0.0,
     */
    @JsonProperty("chargeTheSameDay")
    @Schema(name = "当天充电电量", description = "double", format = "double", example = "114332.4")
    private Double chargeTheSameDay;

    /**
     *    "当前状态": "0充电/1放电/2维护/3就绪/4禁充  int",
     *    "currentStatus": 1,
     */
    @JsonProperty("currentStatus")
    @Schema(name = "当前状态", description = "0充电/1放电/2维护/3就绪/4禁充  int", format = "double", example = "1")
    private Integer currentStatus;

    /**
     *    "电池堆实时充电功率": "float",
     *    "real-timeChargingPowerOfBatteryStack": 0.0,
     */
    @JsonProperty("real-timeChargingPowerOfBatteryStack")
    @Schema(name = "电池堆实时充电功率", description = "double", format = "double", example = "42.4")
    private Double real_timeChargingPowerOfBatteryStack;

    /**
     *    "电池堆实时放电功率": "float",
     *    "real-timeDischargingPowerOfBatteryStack": 1234
     */
    @JsonProperty("real-timeDischargingPowerOfBatteryStack")
    @Schema(name = "电池堆实时放电功率", description = "double", format = "double", example = "62.4")
    private Double real_timeDischargingPowerOfBatteryStack;
}
