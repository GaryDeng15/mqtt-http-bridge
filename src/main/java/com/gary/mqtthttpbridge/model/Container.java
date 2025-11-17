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
 * 集装箱状态数据
 * t_container
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Container implements Serializable {
    /**
     * "空调运行状态":"0关机1开机 int"
     */
    @JsonProperty("ACOperatingStatus")
    @Schema(name = "空调运行状态", description = "0关机1开机 int", format = "int", example = "1")
    private Integer acoperatingstatus;

    /**
     * "开关电源状态": "0关1开 int"
     */
    @JsonProperty("PowerStatus")
    @Schema(name = "开关电源状态", description = "0关1开 int", format = "int", example = "1")
    private Integer powerstatus;

    /**
     * "液冷柜运行状态": "0关机1运行 int"
     */
    @JsonProperty("YLOperatingStatus")
    @Schema(name = "液冷柜运行状态", description = "0关机1运行 int", format = "int", example = "1")
    private Integer yloperatingstatus;

    /**
     * "空调故障": "0正常1故障int "
     */
    @JsonProperty("ACFault")
    @Schema(name = "空调故障", description = "0关机1开机 int", format = "int", example = "1")
    private Integer acfault;

    /**
     * "开关电源故障": "0正常1故障 int "
     */
    @JsonProperty("PowerFault")
    @Schema(name = "开关电源故障", description = "0正常1故障 int ", format = "int", example = "1")
    private Integer powerfault;

    /**
     * "液冷柜运行故障": "0正常1故障 int "
     */
    @JsonProperty("YLOperatingFault")
    @Schema(name = "液冷柜运行故障", description = "0正常1故障 int ", format = "int", example = "1")
    private Integer yloperatingfault;

    /**
     * 记录时间
     */
    @JsonIgnore
    private Date time;

    private static final long serialVersionUID = 1L;

    public Container(
            int acoperatingstatus,
            int powerstatus,
            int yloperatingstatus,
            int acfault,
            int powerfault,
            int yloperatingfault) {
        this.acoperatingstatus = acoperatingstatus;
        this.powerstatus = powerstatus;
        this.yloperatingstatus = yloperatingstatus;
        this.acfault = acfault;
        this.powerfault = powerfault;
        this.yloperatingfault = yloperatingfault;
    }

    @JsonIgnore
    public Boolean isNotAllNull() {
        // 检查Double类型变量
        if (acoperatingstatus == null || powerstatus == null || yloperatingstatus == null
                || acfault == null || powerfault == null
                || yloperatingfault == null ) {
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