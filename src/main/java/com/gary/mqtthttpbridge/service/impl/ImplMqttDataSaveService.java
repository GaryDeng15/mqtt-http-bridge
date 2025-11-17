package com.gary.mqtthttpbridge.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gary.mqtthttpbridge.mapper.BatteryMapper;
import com.gary.mqtthttpbridge.mapper.BatteryOverallMapper;
import com.gary.mqtthttpbridge.mapper.ContainerMapper;
import com.gary.mqtthttpbridge.mapper.PackMapper;
import com.gary.mqtthttpbridge.model.Battery;
import com.gary.mqtthttpbridge.model.BatteryOverall;
import com.gary.mqtthttpbridge.model.Container;
import com.gary.mqtthttpbridge.model.Pack;
import com.gary.mqtthttpbridge.service.ContainerService;
import com.gary.mqtthttpbridge.service.MqttDataSaveService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class ImplMqttDataSaveService implements MqttDataSaveService {
    private JSONObject root;
    private JSONArray dataArray;

    @Resource
    private BatteryOverallMapper batteryOverallMapper;

    @Resource
    private BatteryMapper batteryMapper;

    @Resource
    private ContainerMapper containerMapper;

    @Resource
    private PackMapper packMapper;

    @Override
    public void parseJSON(String payload) {
        root = JSON.parseObject(payload);
        dataArray = root.getJSONArray("data");
        /*for (Object obj : dataArray) {
            JSONObject device = (JSONObject) obj;
            if ("BMS_CELLS".equals(device.getString("no"))) {
                JSONObject tags = device.getJSONObject("tags");
                int clusCeVol001 = tags.getIntValue("ClusCeVol001");
                System.out.println("ClusCeVol001：" + clusCeVol001);
                break;
            }
        }*/
    }

    public void saveBatOverData(BatteryOverall batteryOverall){
        batteryOverallMapper.insert(batteryOverall);
    }

    public void saveBatteryData(Battery battery){
        batteryMapper.insert(battery);
    }

    public void saveContainerData(Container container){
        containerMapper.insert(container);
    }

    public void savePackData(Pack pack){
        packMapper.insert(pack);
    }

    @Override
    public void saveData(String payload) {
        parseJSON(payload);
        //parseBatOverData();
        parseBatteryData();
        //parseContainerData();
        //parsePackData();
    }

    private void parsePackData() {
        Pack pack = new Pack();
        for (Object obj : dataArray) {
            JSONObject device = (JSONObject) obj;
            JSONObject tags = device.getJSONObject("tags");
            if ("BMS".equals(device.getString("no"))) {
                pack.setVoltage(tags.getDouble("RackVoltage"));
                pack.setCurrent(tags.getDouble("RackCurrent"));
                pack.setInsulationresistance(tags.getDouble("RackInsulatVal"));
                pack.setAveragemonomerresistance(tags.getDouble("RackInsulatVal"));
                pack.setAveragemonomertemperature(tags.getDouble("RackAverageTemp"));
                pack.setTerminaltemperature(tags.getDouble("ModuleTemp"));
            }
            if ("EMS".equals(device.getString("no"))) {
                pack.setRealTimeChargingPower(tags.getDouble("chargeStartPower"));
                pack.setRealTimedischargingpower(tags.getDouble("dischargeThreshold"));
            }
        }

        if(pack.isNotAllNull()){
            pack.setPackId("pack1");
            savePackData(pack);
            pack.setPackId("pack2");
            savePackData(pack);
            pack.setPackId("pack3");
            savePackData(pack);
            pack.setPackId("pack4");
            savePackData(pack);
            pack.setPackId("pack5");
            savePackData(pack);
            pack.setPackId("pack6");
            savePackData(pack);
            pack.setPackId("pack7");
            savePackData(pack);
            pack.setPackId("pack8");
            savePackData(pack);
        }
    }

    private void parseContainerData() {
        Container container = new Container();
        for (Object obj : dataArray) {
            JSONObject device = (JSONObject) obj;
            JSONObject tags = device.getJSONObject("tags");
            if ("AC".equals(device.getString("no"))) {
                container.setAcoperatingstatus(tags.getInteger("Read_1"));
                Integer alarm1 = tags.getInteger("Alarm_1");
                Integer alarm2 = tags.getInteger("Alarm_2");
                Integer alarm3 = tags.getInteger("Alarm_3");
                Integer alarm4 = tags.getInteger("Alarm_4");
                Integer alarm28 = tags.getInteger("Alarm_28");

                //0正常1故障
                boolean hasFault = (alarm1 != null && alarm1.equals(1))
                        || (alarm2 != null && alarm2.equals(1))
                        || (alarm3 != null && alarm3.equals(1))
                        || (alarm4 != null && alarm4.equals(1))
                        || (alarm28 != null && alarm28.equals(1));

                container.setAcfault(hasFault ? 1 : 0);

            }

            if ("EMS".equals(device.getString("no"))) {
                System.out.println("EMS");
                // OnOff 0:关机;1:开机;
                Integer onOff = tags.getInteger("OnOff");
                switch (onOff){
                    case 0:
                        container.setPowerstatus(1); //0:正常;1:异常;
                        container.setPowerfault(1);
                        break;
                    case 1:
                        container.setPowerstatus(0); //0:正常;1:异常;
                        container.setPowerfault(0);
                        break;
                    default:
                        container.setPowerstatus(0); //0:正常;1:异常;
                        container.setPowerfault(0);
                }

                //container.setPowerfault(tags.getInteger("OnOff")); //0:正常;1:异常;
            }

            if ("TMS".equals(device.getString("no"))) {
                container.setYloperatingfault(tags.getInteger("Online")); //0:正常;1:异常;
                container.setYloperatingstatus(tags.getInteger("Online")); //0:正常;1:异常;
            }
        }


        if(container.isNotAllNull()){
            saveContainerData(container);
        }

    }

    private void parseBatteryData() {
        List<Battery> batteryList = new ArrayList<>();
        Double totalCurrent = null; // 存储BMS中的总电流

        // 1. 先获取BMS中的总电流（RackCurrent）
        for (Object obj : dataArray) {
            JSONObject device = (JSONObject) obj;
            if ("BMS".equals(device.getString("no"))) {
                JSONObject tags = device.getJSONObject("tags");
                totalCurrent = tags.getDouble("RackCurrent");
                break; // 找到BMS后退出循环
            }
        }

        // 2. 解析BMS_CELLS的电压，生成112个电芯对象
        for (Object obj : dataArray) {
            JSONObject device = (JSONObject) obj;
            if ("BMS_CELLS".equals(device.getString("no"))) {
                JSONObject tags = device.getJSONObject("tags");

                // 循环处理112个电芯（ClusCeVol001到ClusCeVol112）
                for (int i = 1; i <= 112; i++) {
                    // 格式化电芯编号（补前导零，例如1→001，14→014）
                    String volKey = String.format("ClusCeVol%03d", i);
                    String tempKey = String.format("ClusCeTemp%03d", i);
                    Double voltage = tags.getDouble(volKey);
                    Double temp = tags.getDouble(tempKey);

                    // 计算组号和组内编号（14个一组）
                    int group = (i - 1) / 14 + 1; // 组号：1-8
                    int indexInGroup = (i - 1) % 14 + 1; // 组内编号：1-14

                    // 创建电芯对象并设置属性
                    Battery battery = new Battery();
                    battery.setBatteryId(String.format("battery-%d-%d", group, indexInGroup));
                    battery.setVoltage(voltage);
                    battery.setTemperature(temp);
                    battery.setCurrent(totalCurrent); // 所有电芯共用BMS的总电流

                    batteryList.add(battery);
                }
                break; // 处理完BMS_CELLS后退出循环
            }
        }

        for(Battery battery : batteryList){
            if(battery.isNotAllNull()){
                saveBatteryData(battery);
            }
        }

    }

    private void parseBatOverData() {
        BatteryOverall batteryOverall = new BatteryOverall();

        for (Object obj : dataArray) {
            JSONObject device = (JSONObject) obj;
            JSONObject tags = device.getJSONObject("tags");
            if ("BMS".equals(device.getString("no"))) {

                //int clusCeVol001 = tags.getIntValue("ClusCeVol001");
                batteryOverall.setTotalcurrent(tags.getDouble("RackCurrent"));
                batteryOverall.setTotalvoltage(tags.getDouble("RackVoltage"));
                batteryOverall.setMaxbatteryvoltage(tags.getDouble("RackMaxVoltage"));
                // TODO 根据CellId设置PackNumber和Dot
                //batteryOverall.setMaxbatteryvoltage(tags.getDouble("RackMaxVolCellId"));
                //batteryOverall.setMaxvoltagebatterypacknumber(tags.getString("RackMaxVolCellId"));

                batteryOverall.setMinbatteryvoltage(tags.getDouble("RackMinVoltage"));
                //batteryOverall.setMinvoltagebatterypacknumber(tags.getDouble("RackMinVolCellId"));
                //batteryOverall.setMinvoltagebatterydot(tags.getDouble("RackMinVolCellId"));
                batteryOverall.setMaxbatterytemperature(tags.getDouble("RackMaxTemp"));
                //batteryOverall.setMaxbatterytemppackpumber(tags.getDouble("x"));
                //batteryOverall.setMaxbatterytempbatterydot(tags.getDouble("RackMaxTempCellId"));
                batteryOverall.setMinbatterytemperature(tags.getDouble("RackMinTemp"));
                //batteryOverall.setMintempbatterypacknumber();
                //batteryOverall.setMintempbatterypointnumber(tags.getDouble("RackMinTempCellId"));
                batteryOverall.setCumulativecharge(tags.getDouble("AccuCharge"));
                batteryOverall.setCumulativedischarge(tags.getDouble("AccuDischarge"));
                batteryOverall.setDischargecapacity(tags.getDouble("AvailDischarge"));
                batteryOverall.setDischargequantitytoday(tags.getDouble("SingleAccuDischarge"));
                batteryOverall.setChargetoday(tags.getDouble("SingleAccuCharge"));
                batteryOverall.setCurrentstatus(tags.getInteger("RackRunState"));



            }

            if ("EMS".equals(device.getString("no"))) {
                batteryOverall.setRealtimecpofbatterystack(tags.getDouble("chargeStartPower"));
                batteryOverall.setRealtimedcpofbatterystack(tags.getDouble("dischargeThreshold"));

            }
        }

/*        batteryOverall.setMaxvoltagebatterypacknumber("pack1");
        batteryOverall.setMaxvoltagebatterydot("battery-1-3");
        batteryOverall.setMinvoltagebatterypacknumber("pack2");
        batteryOverall.setMinvoltagebatterydot("battery-2-4");
        batteryOverall.setMaxbatterytemppackpumber("pack3");
        batteryOverall.setMaxbatterytempbatterydot("battery-3-5");
        batteryOverall.setMintempbatterypacknumber("pack4");
        batteryOverall.setMintempbatterypointnumber("battery-4-6");*/

        if(batteryOverall.isNotAllNull()){
            saveBatOverData(batteryOverall);
        }

    }
}
