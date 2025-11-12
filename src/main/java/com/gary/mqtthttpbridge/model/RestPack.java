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
public class RestPack {

    /**
     *   "电流": "float",
     *   "current": 12.5,
     */
    @JsonProperty("current")
    @Schema(name = "电流", description = "Double", format = "double", example = "13.5")
    private Double current;

    /**
     *   "电压": "float",
     *   "voltage": 0.0,
     */
    @JsonProperty("voltage")
    @Schema(name = "电压", description = "Double", format = "double", example = "51.34")
    private Double voltage;

    /**
     *  "绝缘电阻": " float ",
     *  "insulationResistance": 0.0
     */
    @JsonProperty("insulationResistance")
    @Schema(name = "绝缘电阻", description = "Double", format = "double", example = "21.444")
    private Double insulationResistance;

    /**
     *  "平均单体电阻": "float",
     *  "averageMonomerResistance":0.0,
     */
    @JsonProperty("averageMonomerResistance")
    @Schema(name = "平均单体电阻", description = "Double", format = "double", example = "56.38")
    private Double averageMonomerResistance;

    /**
     *   "平均单体温度": "float",
     *   "averageMonomerTemperature":0.0,
     */
    @JsonProperty("averageMonomerTemperature")
    @Schema(name = "平均单体温度", description = "Double", format = "double", example = "17.89")
    private Double averageMonomerTemperature;

    /**
     *  "端子温度": "float",
     *  "terminalTemperature": 0.0,
     */
    @JsonProperty("terminalTemperature")
    @Schema(name = "端子温度", description = "Double", format = "double", example = "32.11")
    private Double terminalTemperature;

    /**
     *  "电池包实时充电功率": "float",
     *  "btteryPackReal-timeChargingPower":0.0,
     */
    @JsonProperty("btteryPackReal-timeChargingPower")
    @Schema(name = "电池包实时充电功率", description = "Double", format = "double", example = "76.42")
    private Double btteryPackReal_timeChargingPower;

    /**
     *  "电池包实时放电功率": "float",
     *  "batteryPackReal-timeDischargingPower": 1234
     */
    @JsonProperty("batteryPackReal-timeDischargingPower")
    @Schema(name = "电池包实时放电功率", description = "Double", format = "double", example = "1234.6")
    private Double batteryPackReal_timeDischargingPower;

}
