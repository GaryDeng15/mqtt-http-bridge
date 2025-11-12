package com.gary.mqtthttpbridge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
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
public class RestContainer {

    /**
     * "空调运行状态":"0关机1开机 int",
     * "ACOperatingStatus": 1
     */
    @JsonProperty("ACOperatingStatus")
    @Schema(name = "空调运行状态", description = "0关机,1开机 int", format = "int64", example = "1")
    private Integer ACOperatingStatus;

    /**
     * "开关电源状态": "0关1开 int",
     * "PowerStatus": 1,
     */
    @Schema(name = "开关电源状态", description = "0关机,1开机 int", format = "int64", example = "1")
    @JsonProperty("PowerStatus")
    private Integer powerStatus;

    /**
     * "液冷柜运行状态": "0关机1运行 int",
     * "YLOperatingStatus": 1,
     */
    @Schema(name = "液冷柜运行状态", description = "0关机,1开机 int", format = "int64", example = "1")
    @JsonProperty("YLOperatingStatus")
    private Integer YLOperatingStatus;

    /**
     * "空调故障": "0正常1故障int ",
     * "ACFault": 0,
     */
    @Schema(name = "空调故障", description = "0正常1故障int ", format = "int64", example = "1")
    @JsonProperty("ACFault")
    private Integer ACFault;

    /**
     * "开关电源故障": "0正常1故障 int ",
     * "PowerFault": 0,
     */
    @JsonProperty("PowerFault")
    @Schema(name = "开关电源故障", description = "0正常1故障 int ", format = "int64", example = "1")
    private Integer powerFault;

    /**
     * "液冷柜运行故障": "0正常1故障 int ",
     * "YLOperatingFault": 0
     */
    @JsonProperty("YLOperatingFault")
    @Schema(name = "液冷柜运行故障", description = "0正常1故障 int ", format = "int64", example = "1")
    private Integer YLOperatingFault;
}
