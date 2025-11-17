package com.gary.mqtthttpbridge.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 包数据表
 * t_PackBefore
 */
@Data
public class PackBefore implements Serializable {
    private Integer id;

    /**
     * 电压
     */
    @JsonProperty("voltage")
    private Double Electrictension;

    /**
     * 电流
     */
    @JsonProperty("current")
    private Double electricity;

    /**
     * 绝缘电阻
     */
    @JsonProperty("insulationResistance")
    private Double insulationresistance;

    /**
     * 平均单体电阻
     */
    @JsonProperty("averageMonomerResistance")
    private Double meancellresistance;

    /**
     * 平均单体温度
     */
    @JsonProperty("averageMonomerTemperature")
    private Integer meancelltemperature;

    /**
     * 可放电电量
     */
    @JsonProperty("DischargeCapacity")
    private Double dischargecapacity;

    /**
     * 端子温度
     */
    @JsonProperty("terminalTemperature")
    private Double terminaltemperature;

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
        PackBefore other = (PackBefore) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getElectrictension() == null ? other.getElectrictension() == null : this.getElectrictension().equals(other.getElectrictension()))
            && (this.getElectricity() == null ? other.getElectricity() == null : this.getElectricity().equals(other.getElectricity()))
            && (this.getInsulationresistance() == null ? other.getInsulationresistance() == null : this.getInsulationresistance().equals(other.getInsulationresistance()))
            && (this.getMeancellresistance() == null ? other.getMeancellresistance() == null : this.getMeancellresistance().equals(other.getMeancellresistance()))
            && (this.getMeancelltemperature() == null ? other.getMeancelltemperature() == null : this.getMeancelltemperature().equals(other.getMeancelltemperature()))
            && (this.getDischargecapacity() == null ? other.getDischargecapacity() == null : this.getDischargecapacity().equals(other.getDischargecapacity()))
            && (this.getTerminaltemperature() == null ? other.getTerminaltemperature() == null : this.getTerminaltemperature().equals(other.getTerminaltemperature()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getElectrictension() == null) ? 0 : getElectrictension().hashCode());
        result = prime * result + ((getElectricity() == null) ? 0 : getElectricity().hashCode());
        result = prime * result + ((getInsulationresistance() == null) ? 0 : getInsulationresistance().hashCode());
        result = prime * result + ((getMeancellresistance() == null) ? 0 : getMeancellresistance().hashCode());
        result = prime * result + ((getMeancelltemperature() == null) ? 0 : getMeancelltemperature().hashCode());
        result = prime * result + ((getDischargecapacity() == null) ? 0 : getDischargecapacity().hashCode());
        result = prime * result + ((getTerminaltemperature() == null) ? 0 : getTerminaltemperature().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", electrictension=").append(Electrictension);
        sb.append(", electricity=").append(electricity);
        sb.append(", insulationresistance=").append(insulationresistance);
        sb.append(", meancellresistance=").append(meancellresistance);
        sb.append(", meancelltemperature=").append(meancelltemperature);
        sb.append(", dischargecapacity=").append(dischargecapacity);
        sb.append(", terminaltemperature=").append(terminaltemperature);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}