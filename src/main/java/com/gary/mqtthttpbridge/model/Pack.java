package com.gary.mqtthttpbridge.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 电池pack数据，8个pack的数据
 * t_pack
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pack implements Serializable {
    @JsonIgnore
    private String packId;

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
    @Schema(name = "电压", description = "Double", format = "double", example = "2321.5")
    private Double voltage;

    /**
     * "绝缘电阻": " float "
     */
    @JsonProperty("insulationResistance")
    @Schema(name = "绝缘电阻", description = "Double", format = "double", example = "121.5")
    private Double insulationresistance;

    /**
     * "平均单体电阻": "float"
     */
    @JsonProperty("averageMonomerResistance")
    @Schema(name = "平均单体电阻", description = "Double", format = "double", example = "121.5")
    private Double averagemonomerresistance;

    /**
     * "平均单体温度": "float"
     */
    @JsonProperty("averageMonomerTemperature")
    @Schema(name = "平均单体温度", description = "Double", format = "double", example = "333.5")
    private Double averagemonomertemperature;

    /**
     * "端子温度": "float"
     */
    @JsonProperty("terminalTemperature")
    @Schema(name = "端子温度", description = "Double", format = "double", example = "444.5")
    private Double terminaltemperature;

    /**
     * "电池包实时充电功率": "float"
     */
    @JsonProperty("btteryPackReal-timeChargingPower")
    @Schema(name = "电池包实时充电功率", description = "Double", format = "double", example = "555.5")
    private Double realTimeChargingPower;

    /**
     * "电池包实时放电功率": "float"
     */
    @JsonProperty("batteryPackReal-timeDischargingPower")
    @Schema(name = "电池包实时放电功率", description = "Double", format = "double", example = "666.5")
    private Double realTimedischargingpower;

    @JsonIgnore
    private Date time;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Pack other = (Pack) that;
        return (this.getPackId() == null ? other.getPackId() == null : this.getPackId().equals(other.getPackId()))
            && (this.getCurrent() == null ? other.getCurrent() == null : this.getCurrent().equals(other.getCurrent()))
            && (this.getVoltage() == null ? other.getVoltage() == null : this.getVoltage().equals(other.getVoltage()))
            && (this.getInsulationresistance() == null ? other.getInsulationresistance() == null : this.getInsulationresistance().equals(other.getInsulationresistance()))
            && (this.getAveragemonomerresistance() == null ? other.getAveragemonomerresistance() == null : this.getAveragemonomerresistance().equals(other.getAveragemonomerresistance()))
            && (this.getAveragemonomertemperature() == null ? other.getAveragemonomertemperature() == null : this.getAveragemonomertemperature().equals(other.getAveragemonomertemperature()))
            && (this.getTerminaltemperature() == null ? other.getTerminaltemperature() == null : this.getTerminaltemperature().equals(other.getTerminaltemperature()))
            && (this.getRealTimeChargingPower() == null ? other.getRealTimeChargingPower() == null : this.getRealTimeChargingPower().equals(other.getRealTimeChargingPower()))
            && (this.getRealTimedischargingpower() == null ? other.getRealTimedischargingpower() == null : this.getRealTimedischargingpower().equals(other.getRealTimedischargingpower()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPackId() == null) ? 0 : getPackId().hashCode());
        result = prime * result + ((getCurrent() == null) ? 0 : getCurrent().hashCode());
        result = prime * result + ((getVoltage() == null) ? 0 : getVoltage().hashCode());
        result = prime * result + ((getInsulationresistance() == null) ? 0 : getInsulationresistance().hashCode());
        result = prime * result + ((getAveragemonomerresistance() == null) ? 0 : getAveragemonomerresistance().hashCode());
        result = prime * result + ((getAveragemonomertemperature() == null) ? 0 : getAveragemonomertemperature().hashCode());
        result = prime * result + ((getTerminaltemperature() == null) ? 0 : getTerminaltemperature().hashCode());
        result = prime * result + ((getRealTimeChargingPower() == null) ? 0 : getRealTimeChargingPower().hashCode());
        result = prime * result + ((getRealTimedischargingpower() == null) ? 0 : getRealTimedischargingpower().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", packId=").append(packId);
        sb.append(", current=").append(current);
        sb.append(", voltage=").append(voltage);
        sb.append(", insulationresistance=").append(insulationresistance);
        sb.append(", averagemonomerresistance=").append(averagemonomerresistance);
        sb.append(", averagemonomertemperature=").append(averagemonomertemperature);
        sb.append(", terminaltemperature=").append(terminaltemperature);
        sb.append(", realTimechargingpower=").append(realTimeChargingPower);
        sb.append(", realTimedischargingpower=").append(realTimedischargingpower);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @JsonIgnore
    public Boolean isNotAllNull() {
        // 检查Double类型变量
        if (current == null || voltage == null || insulationresistance == null
                || averagemonomerresistance == null || averagemonomertemperature == null
                || terminaltemperature == null ||  realTimeChargingPower == null || realTimedischargingpower == null) {
            return false;
        }

        // 检查String类型变量
        /*if (packId == null) {
            return false;
        }*/

        // 所有变量均非空
        return true;
    }
}