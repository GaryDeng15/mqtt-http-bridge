package com.gary.mqtthttpbridge.service.impl;

import com.alibaba.fastjson.JSON;
import com.gary.mqtthttpbridge.mapper.BatteryMapper;
import com.gary.mqtthttpbridge.model.Battery;
import com.gary.mqtthttpbridge.model.Pack;
import com.gary.mqtthttpbridge.model.RestPackBatteryData;
import com.gary.mqtthttpbridge.service.BatteryService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gary.mqtthttpbridge.commons.Constant.REDIS_KEY_BATTERY_PREFIX;

/**
 *
 */
@Service
public class ImplBatteryService implements BatteryService {
    @Resource
    private BatteryMapper batteryMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object getLastBattery() {
        return batteryMapper.selectOneByTime();
    }

    @Override
    public Object postOneBattery(Battery newBattery) {
        return batteryMapper.insert(newBattery);
    }

    @Override
    /*public RestPackBatteryData getBatteryByPack(int packNum) {
        // 1. 校验packNum合法性
        if (packNum < 1 || packNum > 8) {
            throw new IllegalArgumentException("packNum必须为1-8之间的整数");
        }

        // 2. 查询该pack下所有电芯的最新数据
        List<Battery> batteryList = batteryMapper.selectLatestByPack(packNum);
        if (batteryList == null) {
            batteryList = new ArrayList<>();
        }

        // 3. 初始化返回对象
        RestPackBatteryData restData = new RestPackBatteryData();

        // 4. 遍历电池列表，按batteryId中的序号映射到对应属性
        for (Battery battery : batteryList) {
            String batteryId = battery.getBatteryId();
            // 解析batteryId，提取序号（如"battery-1-5" → 5）
            Integer index = parseBatteryIndex(batteryId, packNum);
            if (index == null) {
                continue; // 忽略格式不符的ID
            }

            // 根据序号设置到restData的对应属性（battery1到battery14）
            switch (index) {
                case 1:
                    restData.setBattery1(battery);
                    break;
                case 2:
                    restData.setBattery2(battery);
                    break;
                case 3:
                    restData.setBattery3(battery);
                    break;
                case 4:
                    restData.setBattery4(battery);
                    break;
                case 5:
                    restData.setBattery5(battery);
                    break;
                case 6:
                    restData.setBattery6(battery);
                    break;
                case 7:
                    restData.setBattery7(battery);
                    break;
                case 8:
                    restData.setBattery8(battery);
                    break;
                case 9:
                    restData.setBattery9(battery);
                    break;
                case 10:
                    restData.setBattery10(battery);
                    break;
                case 11:
                    restData.setBattery11(battery);
                    break;
                case 12:
                    restData.setBattery12(battery);
                    break;
                case 13:
                    restData.setBattery13(battery);
                    break;
                case 14:
                    restData.setBattery14(battery);
                    break;
                default:
                    // 忽略序号超出1-14的情况
                    break;
            }
        }

        return restData;
    }*/

    /**
     * 根据packNum查询对应包下的所有电芯数据（从Redis读取）
     * 原有核心逻辑（校验、解析、属性映射）完全保留
     */
    public RestPackBatteryData getBatteryByPack(int packNum) {
        // 1. 校验packNum合法性（原有逻辑不变）
        if (packNum < 1 || packNum > 8) {
            throw new IllegalArgumentException("packNum必须为1-8之间的整数");
        }

        // 2. 从Redis查询该pack下所有电芯的最新数据（核心修改点）
        List<Battery> batteryList = getBatteryListFromRedisByPack(packNum);
        if (batteryList == null) {
            batteryList = new ArrayList<>();
        }

        // 3. 初始化返回对象（原有逻辑不变）
        RestPackBatteryData restData = new RestPackBatteryData();

        // 4. 遍历电池列表，按batteryId中的序号映射到对应属性（原有逻辑不变）
        for (Battery battery : batteryList) {
            String batteryId = battery.getBatteryId();
            // 解析batteryId，提取序号（如"battery-1-5" → 5）
            Integer index = parseBatteryIndex(batteryId, packNum);
            if (index == null) {
                continue; // 忽略格式不符的ID
            }

            // 根据序号设置到restData的对应属性（battery1到battery14）
            switch (index) {
                case 1:
                    restData.setBattery1(battery);
                    break;
                case 2:
                    restData.setBattery2(battery);
                    break;
                case 3:
                    restData.setBattery3(battery);
                    break;
                case 4:
                    restData.setBattery4(battery);
                    break;
                case 5:
                    restData.setBattery5(battery);
                    break;
                case 6:
                    restData.setBattery6(battery);
                    break;
                case 7:
                    restData.setBattery7(battery);
                    break;
                case 8:
                    restData.setBattery8(battery);
                    break;
                case 9:
                    restData.setBattery9(battery);
                    break;
                case 10:
                    restData.setBattery10(battery);
                    break;
                case 11:
                    restData.setBattery11(battery);
                    break;
                case 12:
                    restData.setBattery12(battery);
                    break;
                case 13:
                    restData.setBattery13(battery);
                    break;
                case 14:
                    restData.setBattery14(battery);
                    break;
                default:
                    // 忽略序号超出1-14的情况
                    break;
            }
        }

        return restData;
    }

    // ========== 新增：从Redis读取指定pack下的所有电芯数据 ==========
    private List<Battery> getBatteryListFromRedisByPack(int packNum) {
        List<Battery> batteryList = new ArrayList<>();

        // 步骤1：遍历该pack下的所有batteryId（packNum对应batteryId的第一个数字，如pack1对应battery-1-*）
        // 生成该pack的batteryId前缀（如pack1 → battery-1-）
        String batteryIdPrefix = "battery-" + packNum + "-";

        // 步骤2：匹配Redis中所有以"mqtt:battery:latest:battery-{packNum}-"开头的Key
        // 注意：keys()方法在生产环境建议替换为scan()，避免阻塞Redis
        /*Set<String> redisKeys = redisTemplate.keys(REDIS_KEY_BATTERY_PREFIX + batteryIdPrefix + "*");
        if (redisKeys == null || redisKeys.isEmpty()) {
            return batteryList; // 无数据返回空列表
        }*/

        for(int i = 1; i<=14; i++){
            // 读取Hash结构的所有字段和值
            String tempKey = batteryIdPrefix+i;
            Map<Object, Object> hashMap = redisTemplate.opsForHash().entries(REDIS_KEY_BATTERY_PREFIX + tempKey);
            // 转换为Battery实体类（根据Hash字段赋值）
            //Battery battery = convertHashToBattery(hashMap);
            Battery battery = JSON.parseObject(JSON.toJSONString(hashMap), Battery.class);
            if (battery != null) {
                batteryList.add(battery);
            }
        }

        /*// 步骤3：遍历每个Redis Key，读取Hash结构并转换为Battery对象
        for (String redisKey : redisKeys) {
            // 读取Hash结构的所有字段和值
            Map<Object, Object> hashMap = redisTemplate.opsForHash().entries(redisKey);
            if (hashMap.isEmpty()) {
                continue;
            }

            // 转换为Battery实体类（根据Hash字段赋值）
            //Battery battery = convertHashToBattery(hashMap);
            Battery battery = JSON.parseObject(JSON.toJSONString(hashMap), Battery.class);
            if (battery != null) {
                batteryList.add(battery);
            }
        }*/

        return batteryList;
    }

    // ========== 工具方法：将Redis Hash转换为Battery实体 ==========
    private Battery convertHashToBattery(Map<Object, Object> hashMap) {
        try {
            Battery battery = new Battery();
            // 按Battery类的字段名，从Hash中取值并赋值（需与存储时的字段名一致）
            battery.setBatteryId((String) hashMap.get("batteryId"));
            battery.setVoltage((Double) hashMap.get("voltage"));
            battery.setTemperature((Double) hashMap.get("temperature"));
            battery.setCurrent((Double) hashMap.get("current"));
            // 若Battery有其他字段，继续补充赋值（如createTime/updateTime）
            // battery.setCreateTime((Date) hashMap.get("createTime"));
            return battery;
        } catch (Exception e) {
            // 类型转换异常时返回null，避免影响整体查询
            e.printStackTrace();
            return null;
        }
    }

    private Integer parseBatteryIndex(String batteryId, int packNum) {
        // 新增：先判断batteryId是否为null
        if (batteryId == null) {
            // 日志记录（可选）：方便排查空值来源
            //log.warn("batteryId is null, packNum: {}", packNum);
            return null;
        }

        // 正则匹配格式：battery-{packNum}-{index}（index为1-14）
        String regex = String.format("^battery-%d-(\\d+)$", packNum);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(batteryId); // 此时batteryId一定非空

        if (matcher.matches()) {
            String indexStr = matcher.group(1); // 获取捕获组中的序号（如"5"）
            try {
                int index = Integer.parseInt(indexStr);
                if (index >= 1 && index <= 14) {
                    return index;
                }
            } catch (NumberFormatException e) {
                //log.warn("batteryId {} 序号格式错误", batteryId, e);
            }
        }
        return null; // 格式不符或序号无效
    }
}
