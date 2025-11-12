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
public class RestPackBatteryData {

    @JsonProperty("battery1")
    @Schema(name = "battery1", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery1;

    @JsonProperty("battery2")
    @Schema(name = "battery2", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery2;

    @JsonProperty("battery3")
    @Schema(name = "battery3", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery3;

    @JsonProperty("battery4")
    @Schema(name = "battery4", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery4;

    @JsonProperty("battery5")
    @Schema(name = "battery5", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery5;

    @JsonProperty("battery6")
    @Schema(name = "battery6", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery6;

    @JsonProperty("battery7")
    @Schema(name = "battery7", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery7;

    @JsonProperty("battery8")
    @Schema(name = "battery8", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery8;

    @JsonProperty("battery9")
    @Schema(name = "battery9", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery9;

    @JsonProperty("battery10")
    @Schema(name = "battery10", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery10;

    @JsonProperty("battery11")
    @Schema(name = "battery11", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery11;

    @JsonProperty("battery12")
    @Schema(name = "battery12", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery12;

    @JsonProperty("battery13")
    @Schema(name = "battery13", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery13;

    @JsonProperty("battery14")
    @Schema(name = "battery14", description = "RestBattery", format = "RestBattery", example = "{\"current\": 12.5,\"voltage\": 43.565,\"temperature\": 833.31}")
    private RestBattery battery14;
}
