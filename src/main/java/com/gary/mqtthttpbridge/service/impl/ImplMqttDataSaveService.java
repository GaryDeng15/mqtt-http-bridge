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
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Service
public class ImplMqttDataSaveService implements MqttDataSaveService {

    // 线程池：核心线程数4，最大线程数4，避免过多线程竞争数据库连接
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    // 线程安全的共享变量（使用volatile确保可见性）
    private volatile JSONObject root;
    private volatile JSONArray dataArray;

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
        synchronized (this){
            root = JSON.parseObject(payload);
            dataArray = root.getJSONArray("data");
        }
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

        /*parseBatOverData();     //BMS EMS BMS_CELLS
        parseBatteryData();     //BMS BMS_CELLS
        parseContainerData();   //AC EMS TMS
        parsePackData();        //BMS EMS*/

        try {
            // 2. 提交四个任务到线程池并行执行
            CompletableFuture<Void> batOverFuture = CompletableFuture.runAsync(this::parseBatOverData, executorService);
            CompletableFuture<Void> batteryFuture = CompletableFuture.runAsync(this::parseBatteryData, executorService);
            CompletableFuture<Void> containerFuture = CompletableFuture.runAsync(this::parseContainerData, executorService);
            CompletableFuture<Void> packFuture = CompletableFuture.runAsync(this::parsePackData, executorService);

            // 3. 等待所有任务完成（最多等待30秒，防止无限阻塞）
            CompletableFuture.allOf(
                    batOverFuture,
                    batteryFuture,
                    containerFuture,
                    packFuture
            ).get(30, TimeUnit.SECONDS);

        } catch (Exception e) {
            // 4. 异常处理：打印日志并重新抛出（根据业务需求调整）
            e.printStackTrace();
            throw new RuntimeException("MQTT数据保存失败", e);
        }

    }

    /**
     * 销毁时关闭线程池，释放资源
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            // 等待60秒强制关闭
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        }
    }

    private void parsePackData() {
        // 局部变量拷贝共享数据，避免多线程并发修改问题
        JSONArray localDataArray = dataArray;
        if (localDataArray == null) return;

        // 存储8个pack的电压（pack1~pack8）
        double[] packVoltages = new double[8];

        //Pack pack = new Pack();
        Double aPhaseActivePower = 0.0, bPhaseActivePower = 0.0, cPhaseActivePower = 0.0;
        Pack basePack = new Pack();
        for (Object obj : localDataArray) {
            JSONObject device = (JSONObject) obj;
            JSONObject tags = device.getJSONObject("tags");

            // 1. 解析BMS_CELLS：按14个电芯一组计算pack电压
            if ("BMS_CELLS".equals(device.getString("no"))) {
                // 循环处理112个电芯（ClusCeVol001~ClusCeVol112）
                for (int i = 1; i <= 112; i++) {
                    String volKey = String.format("ClusCeVol%03d", i);
                    Double cellVoltage = tags.getDouble(volKey) / 1000;

                    // 跳过空值（避免空指针）
                    if (cellVoltage == null) continue;

                    // 计算所属pack序号（0~7对应pack1~pack8）
                    int packIndex = (i - 1) / 14; // 1-14→0，15-28→1，...，106-112→7
                    // 累加当前电芯电压到对应pack
                    packVoltages[packIndex] += cellVoltage;
                }
            }

            else if ("BMS".equals(device.getString("no"))) {
                //pack.setVoltage(tags.getDouble("RackVoltage"));
                // 初始化pack基础数据（后续每个pack复用此基础数据）

                basePack.setCurrent(tags.getDouble("RackCurrent"));
                basePack.setInsulationresistance(tags.getDouble("RackInsulatVal"));
                basePack.setAveragemonomerresistance(tags.getDouble("RackInsulatVal"));
                basePack.setAveragemonomertemperature(tags.getDouble("RackAverageTemp"));
                basePack.setTerminaltemperature(tags.getDouble("ModuleTemp"));
            }

            // TODO
            /*else if ("FZ".equals(device.getString("no"))) {
                aPhaseActivePower = tags.getDouble("AphaseActivePower");
            }
            else if ("FZ2".equals(device.getString("no"))) {
                bPhaseActivePower = tags.getDouble("BphaseActivePower");
            }
            else if ("FZ3".equals(device.getString("no"))) {
                cPhaseActivePower = tags.getDouble("CphaseActivePower");
            }*/

            else if ("PCS".equals(device.getString("no"))) {
                aPhaseActivePower = tags.getDouble("AphaseActPower");
                bPhaseActivePower = tags.getDouble("BphaseActPower");
                cPhaseActivePower = tags.getDouble("CphaseActPower");
            }


        }
        // 实时充电功率 = A相有功功率 + B相有功功率 + C相有功功率
        basePack.setRealTimeChargingPower(-1 * (aPhaseActivePower + bPhaseActivePower + cPhaseActivePower));
        // 实时放电功率 = -( A相有功功率 + B相有功功率 + C相有功功率 )
        basePack.setRealTimedischargingpower(aPhaseActivePower + bPhaseActivePower + cPhaseActivePower);

        // 4. 为每个pack设置专属电压和公共数据，然后保存
        for (int i = 0; i < 8; i++) {
            Pack pack = new Pack();

            // 设置当前pack的专属电压（从BMS_CELLS分组计算得出）
            pack.setVoltage(packVoltages[i]); // 新增：每组电芯总电压
            //pack.(packVoltages[i] / 14); // 新增：组内电芯平均电压（可选）

            // 设置公共数据（保留原有逻辑）
            //pack.setVoltage(basePack.getVoltage()); // 整组总电压（原有逻辑）
            pack.setCurrent(basePack.getCurrent());
            pack.setInsulationresistance(basePack.getInsulationresistance());
            pack.setAveragemonomerresistance(basePack.getAveragemonomerresistance());
            pack.setAveragemonomertemperature(basePack.getAveragemonomertemperature());
            pack.setTerminaltemperature(basePack.getTerminaltemperature());

            // 功率相关（保留原有逻辑）
            pack.setRealTimeChargingPower(basePack.getRealTimeChargingPower());
            pack.setRealTimedischargingpower(basePack.getRealTimedischargingpower());

            // 设置packId并保存（pack1~pack8）
            pack.setPackId("pack" + (i + 1));

            // 非空校验后保存（确保有有效数据才保存）
            if (pack.isNotAllNull()) {
                savePackData(pack);
            }
        }
    }

    private void parseContainerData() {
        // 局部变量拷贝共享数据，避免多线程并发修改问题
        JSONArray localDataArray = dataArray;
        if (localDataArray == null) return;

        Container container = new Container();
        for (Object obj : localDataArray) {
            JSONObject device = (JSONObject) obj;
            JSONObject tags = device.getJSONObject("tags");
            if ("AC".equals(device.getString("no"))) {
                System.out.println("%nAC%n");
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
                System.out.println("%nEMS%n");
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
                System.out.println("%nTMS%n");
                container.setYloperatingfault(tags.getInteger("Online")); //0:正常;1:异常;
                container.setYloperatingstatus(tags.getInteger("Online")); //0:正常;1:异常;
            }
        }


        if(container.isNotAllNull()){
            saveContainerData(container);
        }

    }

    private void parseBatteryData() {
        // 局部变量拷贝共享数据，避免多线程并发修改问题
        JSONArray localDataArray = dataArray;
        if (localDataArray == null) return;

        List<Battery> batteryList = new ArrayList<>();
        Double totalCurrent = null; // 存储BMS中的总电流

        // 1. 先获取BMS中的总电流（RackCurrent）
        for (Object obj : localDataArray) {
            JSONObject device = (JSONObject) obj;
            if ("BMS".equals(device.getString("no"))) {
                System.out.println("%nBMS%n");
                JSONObject tags = device.getJSONObject("tags");
                totalCurrent = tags.getDouble("RackCurrent");
                break; // 找到BMS后退出循环
            }
        }

        // 2. 解析BMS_CELLS的电压，生成112个电芯对象
        for (Object obj : localDataArray) {
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

    /*private void parseBatOverData() {
        BatteryOverall batteryOverall = new BatteryOverall();

        for (Object obj : dataArray) {
            JSONObject device = (JSONObject) obj;
            JSONObject tags = device.getJSONObject("tags");
            if ("BMS".equals(device.getString("no"))) {

                //int clusCeVol001 = tags.getIntValue("ClusCeVol001");
                batteryOverall.setTotalcurrent(tags.getDouble("RackCurrent"));
                batteryOverall.setTotalvoltage(tags.getDouble("RackVoltage"));
                batteryOverall.setMaxbatteryvoltage(tags.getDouble("RackMaxVoltage"));
                //根据CellId设置PackNumber和Dot
                //batteryOverall.setMaxvoltagebatterydot(tags.getDouble("RackMaxVolCellId"));
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

*//*        batteryOverall.setMaxvoltagebatterypacknumber("pack1");
        batteryOverall.setMaxvoltagebatterydot("battery-1-3");
        batteryOverall.setMinvoltagebatterypacknumber("pack2");
        batteryOverall.setMinvoltagebatterydot("battery-2-4");
        batteryOverall.setMaxbatterytemppackpumber("pack3");
        batteryOverall.setMaxbatterytempbatterydot("battery-3-5");
        batteryOverall.setMintempbatterypacknumber("pack4");
        batteryOverall.setMintempbatterypointnumber("battery-4-6");*//*

        if(batteryOverall.isNotAllNull()){
            saveBatOverData(batteryOverall);
        }

    }*/



    private void parseBatOverData() {
        // 局部变量拷贝共享数据，避免多线程并发修改问题
        JSONArray localDataArray = dataArray;
        if (localDataArray == null) return;

        BatteryOverall batteryOverall = new BatteryOverall();
        // 存储电芯电压数据：key=电芯编号(1-112), value=电压值
        Map<Integer, Double> cellVoltageMap = new HashMap<>();
        // 存储电芯温度数据：key=电芯编号(1-112), value=温度值
        Map<Integer, Double> cellTemperatureMap = new HashMap<>();

        Double aPhaseActivePower = 0.0, bPhaseActivePower = 0.0, cPhaseActivePower = 0.0;

        for (Object obj : localDataArray) {
            JSONObject device = (JSONObject) obj;
            String deviceNo = device.getString("no");
            JSONObject tags = device.getJSONObject("tags");

            // 解析BMS设备的整体数据
            if ("BMS".equals(deviceNo)) {
                batteryOverall.setTotalcurrent(tags.getDouble("RackCurrent"));
                batteryOverall.setTotalvoltage(tags.getDouble("RackVoltage"));
                batteryOverall.setMaxbatteryvoltage(tags.getDouble("RackMaxVoltage"));
                batteryOverall.setMinbatteryvoltage(tags.getDouble("RackMinVoltage"));
                batteryOverall.setMaxbatterytemperature(tags.getDouble("RackMaxTemp"));
                batteryOverall.setMinbatterytemperature(tags.getDouble("RackMinTemp"));
                batteryOverall.setCumulativecharge(tags.getDouble("AccuCharge"));
                batteryOverall.setCumulativedischarge(tags.getDouble("AccuDischarge"));
                batteryOverall.setDischargecapacity(tags.getDouble("AvailDischarge"));
                batteryOverall.setDischargequantitytoday(tags.getDouble("SingleAccuDischarge"));
                batteryOverall.setChargetoday(tags.getDouble("SingleAccuCharge"));
                batteryOverall.setCurrentstatus(tags.getInteger("RackRunState"));
            }

            // 解析EMS设备的CPO数据
            else if ("PCS".equals(device.getString("no"))) {
                aPhaseActivePower = tags.getDouble("AphaseActPower");
                bPhaseActivePower = tags.getDouble("BphaseActPower");
                cPhaseActivePower = tags.getDouble("CphaseActPower");

                // 实时充电功率 = A相有功功率 + B相有功功率 + C相有功功率
                // -1 * (aPhaseActivePower + bPhaseActivePower + cPhaseActivePower)
                // 实时放电功率 = -( A相有功功率 + B相有功功率 + C相有功功率 )
                // aPhaseActivePower + bPhaseActivePower + cPhaseActivePower
                batteryOverall.setRealtimecpofbatterystack(-1 * (aPhaseActivePower + bPhaseActivePower + cPhaseActivePower));
                batteryOverall.setRealtimedcpofbatterystack(aPhaseActivePower + bPhaseActivePower + cPhaseActivePower);
            }

            // 实时充电功率 = A相有功功率 + B相有功功率 + C相有功功率
            // -1 * (aPhaseActivePower + bPhaseActivePower + cPhaseActivePower)
            // 实时放电功率 = -( A相有功功率 + B相有功功率 + C相有功功率 )
            // aPhaseActivePower + bPhaseActivePower + cPhaseActivePower

            // 解析BMS_CELLS设备的112个电芯数据
            else if ("BMS_CELLS".equals(deviceNo)) {
                // 解析电压数据（ClusCeVol001 ~ ClusCeVol112）
                for (int i = 1; i <= 112; i++) {
                    String voltageKey = String.format("ClusCeVol%03d", i);
                    if (tags.containsKey(voltageKey)) {
                        double voltage = tags.getDoubleValue(voltageKey);
                        cellVoltageMap.put(i, voltage);
                    }
                }

                // 解析温度数据（ClusCeTemp001 ~ ClusCeTemp112）
                for (int i = 1; i <= 112; i++) {
                    String tempKey = String.format("ClusCeTemp%03d", i);
                    if (tags.containsKey(tempKey)) {
                        double temperature = tags.getDoubleValue(tempKey);
                        cellTemperatureMap.put(i, temperature);
                    }
                }
            }
        }

        // 处理电压数据：找出最高/最低电压对应的组号和点号
        processVoltageData(cellVoltageMap, batteryOverall);
        // 处理温度数据：找出最高/最低温度对应的组号和点号
        processTemperatureData(cellTemperatureMap, batteryOverall);

        // 保存数据（非空校验）
        if (batteryOverall.isNotAllNull()) {
            saveBatOverData(batteryOverall);
        }
    }

    /**
     * 处理电压数据，设置最高/最低电压对应的组号和点号
     * @param cellVoltageMap 电芯电压映射（key=电芯编号1-112，value=电压值）
     * @param batteryOverall 电池整体数据对象
     */
    private void processVoltageData(Map<Integer, Double> cellVoltageMap, BatteryOverall batteryOverall) {
        if (cellVoltageMap.isEmpty()) {
            return;
        }

        int maxVoltageCellId = -1;
        int minVoltageCellId = -1;
        double maxVoltage = Double.MIN_VALUE;
        double minVoltage = Double.MAX_VALUE;

        // 遍历找出最高/最低电压对应的电芯编号
        for (Map.Entry<Integer, Double> entry : cellVoltageMap.entrySet()) {
            int cellId = entry.getKey();
            double voltage = entry.getValue();

            if (voltage > maxVoltage) {
                maxVoltage = voltage;
                maxVoltageCellId = cellId;
            }

            if (voltage < minVoltage) {
                minVoltage = voltage;
                minVoltageCellId = cellId;
            }
        }

        // 设置最高电压的组号和点号
        if (maxVoltageCellId != -1) {
            CellPosition maxPos = calculateCellPosition(maxVoltageCellId);
            batteryOverall.setMaxvoltagebatterypacknumber(maxPos.getPackNumber());
            batteryOverall.setMaxvoltagebatterydot(maxPos.getDotId());
        }

        // 设置最低电压的组号和点号
        if (minVoltageCellId != -1) {
            CellPosition minPos = calculateCellPosition(minVoltageCellId);
            batteryOverall.setMinvoltagebatterypacknumber(minPos.getPackNumber());
            batteryOverall.setMinvoltagebatterydot(minPos.getDotId());
        }
    }

    /**
     * 处理温度数据，设置最高/最低温度对应的组号和点号
     * @param cellTemperatureMap 电芯温度映射（key=电芯编号1-112，value=温度值）
     * @param batteryOverall 电池整体数据对象
     */
    private void processTemperatureData(Map<Integer, Double> cellTemperatureMap, BatteryOverall batteryOverall) {
        if (cellTemperatureMap.isEmpty()) {
            return;
        }

        int maxTempCellId = -1;
        int minTempCellId = -1;
        double maxTemp = Double.MIN_VALUE;
        double minTemp = Double.MAX_VALUE;

        // 遍历找出最高/最低温度对应的电芯编号
        for (Map.Entry<Integer, Double> entry : cellTemperatureMap.entrySet()) {
            int cellId = entry.getKey();
            double temp = entry.getValue();

            if (temp > maxTemp) {
                maxTemp = temp;
                maxTempCellId = cellId;
            }

            if (temp < minTemp) {
                minTemp = temp;
                minTempCellId = cellId;
            }
        }

        // 设置最高温度的组号和点号
        if (maxTempCellId != -1) {
            CellPosition maxPos = calculateCellPosition(maxTempCellId);
            batteryOverall.setMaxbatterytemppackpumber(maxPos.getPackNumber());
            batteryOverall.setMaxbatterytempbatterydot(maxPos.getDotId());
        }

        // 设置最低温度的组号和点号
        if (minTempCellId != -1) {
            CellPosition minPos = calculateCellPosition(minTempCellId);
            batteryOverall.setMintempbatterypacknumber(minPos.getPackNumber());
            batteryOverall.setMintempbatterypointnumber(minPos.getDotId());
        }
    }

    /**
     * 计算电芯的组号和点号
     * 规则：14个电芯为一组，编号1-14→pack1，15-28→pack2，...，106-112→pack8
     * 点号格式：battery-{组号}-{组内序号}
     * @param cellId 电芯编号（1-112）
     * @return 电芯位置信息（组号+点号）
     */
    private CellPosition calculateCellPosition(int cellId) {
        if (cellId < 1 || cellId > 112) {
            throw new IllegalArgumentException("电芯编号必须在1-112之间：" + cellId);
        }

        // 计算组号（向上取整：(cellId + 14 - 1) / 14）
        int packNo = (cellId + 13) / 14;
        // 计算组内序号（1-14）
        int dotNoInPack = cellId - (packNo - 1) * 14;

        // 组装返回结果
        String packNumber = "pack" + packNo;
        String dotId = String.format("battery-%d-%d", packNo, dotNoInPack);
        return new CellPosition(packNumber, dotId);
    }

    /**
     * 内部辅助类：存储电芯的组号和点号
     */
    private static class CellPosition {
        private final String packNumber;
        private final String dotId;

        public CellPosition(String packNumber, String dotId) {
            this.packNumber = packNumber;
            this.dotId = dotId;
        }

        public String getPackNumber() {
            return packNumber;
        }

        public String getDotId() {
            return dotId;
        }
    }


}
