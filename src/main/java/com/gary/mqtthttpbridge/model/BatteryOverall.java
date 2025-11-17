package com.gary.mqtthttpbridge.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 电池总体数据
 * t_battery_overall
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryOverall implements Serializable {
    /**
     * "总电流": "float"
     */
    @JsonProperty("totalCurrent")
    @Schema(name = "总电流", description = "Double", format = "double", example = "12.5")
    private Double totalcurrent;

    /**
     * "总电压": "float"
     */
    @JsonProperty("totalVoltage")
    @Schema(name = "总电压", description = "Double", format = "double", example = "22.5")
    private Double totalvoltage;

    /**
     * "最高电池电压": "float"
     */
    @JsonProperty("maximumBatteryVoltage")
    @Schema(name = "最高电池电压", description = "Double", format = "double", example = "23.5")
    private Double maxbatteryvoltage;

    /**
     * "最高电压电池组号": "string"
     */
    @JsonProperty("maximumVoltageBatteryPackNumber")
    @Schema(name = "最高电压电池组号", description = "String", format = "String", example = "pack4")
    private String maxvoltagebatterypacknumber;

    /**
     * "最高电压电池点号": "string"
     */
    @JsonProperty("maximumVoltageBatteryDOT")
    @Schema(name = "最高电压电池点号", description = "String", format = "String", example = "pack4")
    private String maxvoltagebatterydot;

    /**
     * "最低电池电压": "float"
     */
    @JsonProperty("minimumBatteryVoltage")
    @Schema(name = "最低电池电压", description = "Double", format = "double", example = "24.5")
    private Double minbatteryvoltage;

    /**
     * "最低电压电池组号": "string"
     */
    @JsonProperty("lowestVoltageBatteryPackNumber")
    @Schema(name = "最低电压电池组号", description = "String", format = "String", example = "pack4")
    private String minvoltagebatterypacknumber;

    /**
     * "最低电压电池点号": "string"
     */
    @JsonProperty("lowestVoltageBatteryDOT")
    @Schema(name = "最低电压电池点号", description = "String", format = "String", example = "battery-3-8")
    private String minvoltagebatterydot;

    /**
     * "最高电池温度": "float"
     */
    @JsonProperty("maximumBatteryTemperature")
    @Schema(name = "最高电池温度", description = "Double", format = "double", example = "28.5")
    private Double maxbatterytemperature;

    /**
     * "最高电池温度组号": "string"
     */
    @JsonProperty("highestBatteryTemperaturePackPumber")
    @Schema(name = "最高电池温度组号", description = "String", format = "String", example = "pack5")
    private String maxbatterytemppackpumber;

    /**
     * "最高电池温度点号": "string"
     */
    @JsonProperty("maximumBatteryTemperatureBatteryDOT")
    @Schema(name = "最高电池温度点号", description = "String", format = "String", example = "battery-4-7")
    private String maxbatterytempbatterydot;

    /**
     * "最低电池温度": "float"
     */
    @JsonProperty("minimumBatteryTemperature")
    @Schema(name = "最低电池温度", description = "Double", format = "double", example = "29.5")
    private Double minbatterytemperature;

    /**
     * "最低温度电池组号": "string"
     */
    @JsonProperty("lowestTemperatureBatteryPackNumber")
    @Schema(name = "最低温度电池组号", description = "String", format = "String", example = "pack2")
    private String mintempbatterypacknumber;

    /**
     * "最低温度电池点号": "string"
     */
    @JsonProperty("minimumTemperatureBatteryPointNumber")
    @Schema(name = "最低温度电池点号", description = "String", format = "String", example = "battery-5-7")
    private String mintempbatterypointnumber;

    /**
     * "累计充电电量": "float"
     */
    @JsonProperty("cumulativeCharge")
    @Schema(name = "累计充电电量", description = "Double", format = "double", example = "30.5")
    private Double cumulativecharge;

    /**
     * "累计放电电量": "float"
     */
    @JsonProperty("cumulativeDischarge")
    @Schema(name = "累计放电电量", description = "Double", format = "double", example = "30.5")
    private Double cumulativedischarge;

    /**
     * "可放电电量": "float"
     */
    @JsonProperty("dischargeCapacity")
    @Schema(name = "可放电电量", description = "Double", format = "double", example = "31.5")
    private Double dischargecapacity;

    /**
     * "当天放电电量": "float"
     */
    @JsonProperty("dischargeQuantityOnTheSameDay")
    @Schema(name = "当天放电电量", description = "Double", format = "double", example = "32.5")
    private Double dischargequantitytoday;

    /**
     * "当天充电电量": "float"
     */
    @JsonProperty("chargeTheSameDay")
    @Schema(name = "当天充电电量", description = "Double", format = "double", example = "33.5")
    private Double chargetoday;

    /**
     * "当前状态": "0充电/1放电/2维护/3就绪/4禁充 int"
     */
    @JsonProperty("currentStatus")
    @Schema(name = "当前状态", description = "Integer", format = "int", example = "3")
    private Integer currentstatus;

    /**
     * "电池堆实时充电功率": "float"
     */
    @JsonProperty("real-timeChargingPowerOfBatteryStack")
    @Schema(name = "电池堆实时充电功率", description = "Double", format = "double", example = "34.5")
    private Double realtimecpofbatterystack;

    /**
     * "电池堆实时放电功率": "float"
     */
    @JsonProperty("real-timeDischargingPowerOfBatteryStack")
    @Schema(name = "电池堆实时放电功率", description = "Double", format = "double", example = "35.5")
    private Double realtimedcpofbatterystack;

    @JsonIgnore
    private Date time;

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    public Boolean isNotAllNull() {
        // 检查Double类型变量
        if (totalcurrent == null || totalvoltage == null || maxbatteryvoltage == null
                || minbatteryvoltage == null || maxbatterytemperature == null
                || minbatterytemperature == null || cumulativecharge == null
                || cumulativedischarge == null || dischargecapacity == null
                || dischargequantitytoday == null || chargetoday == null
                || realtimecpofbatterystack == null || realtimedcpofbatterystack == null) {
            return false;
        }

        // 检查String类型变量
        if (maxvoltagebatterypacknumber == null || maxvoltagebatterydot == null
                || minvoltagebatterypacknumber == null || minvoltagebatterydot == null
                || maxbatterytemppackpumber == null || maxbatterytempbatterydot == null
                || mintempbatterypacknumber == null || mintempbatterypointnumber == null) {
            return false;
        }

        // 检查Integer类型变量
        if (currentstatus == null) {
            return false;
        }

        // 检查Date类型变量
        /*if (time == null) {
            return false;
        }*/

        // 所有变量均非空
        return true;
    }
}