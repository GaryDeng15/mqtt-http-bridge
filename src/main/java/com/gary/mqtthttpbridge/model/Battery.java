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
 * pack电芯数据
 * t_battery
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battery implements Serializable {
    /**
     * "电流": "float"
     */
    @JsonProperty("current")
    @Schema(name = "电流", description = "Double", format = "double", example = "4321.5")
    private Double current;

    /**
     * "电压": "float"
     */
    @JsonProperty("voltage")
    @Schema(name = "电压", description = "Double", format = "double", example = "4321.5")
    private Double voltage;

    /**
     * "温度": "float"
     */
    @JsonProperty("temperature")
    @Schema(name = "温度", description = "Double", format = "double", example = "4321.5")
    private Double temperature;

    @JsonIgnore
    private Date time;

    @JsonIgnore
    private String batteryId;

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    public Boolean isNotAllNull() {
        // 检查Double类型变量
        if (current == null || voltage == null || temperature == null) {
            return false;
        }

        // 检查String类型变量
        if (batteryId == null) {
            return false;
        }

        // 所有变量均非空
        return true;
    }
}