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
public class RestBattery {

    /**
     * "电流": "float",
     * "current": 12.5
     */
    @JsonProperty("current")
    @Schema(name = "电流", description = "Double", format = "double", example = "12.5")
    private Double current;

    /**
     * "电压": "float",
     * "voltage": 0.0
     */
    @JsonProperty("voltage")
    @Schema(name = "电压", description = "Double", format = "double", example = "43.565")
    private Double voltage;

    /**
     * "温度": "float",
     * "temperature": 0.0
     */
    @JsonProperty("temperature")
    @Schema(name = "温度", description = "Double", format = "double", example = "833.31")
    private Double temperature;
}
