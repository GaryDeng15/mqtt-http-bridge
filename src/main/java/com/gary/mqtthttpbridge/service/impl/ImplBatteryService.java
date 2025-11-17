package com.gary.mqtthttpbridge.service.impl;

import com.gary.mqtthttpbridge.mapper.BatteryMapper;
import com.gary.mqtthttpbridge.model.Battery;
import com.gary.mqtthttpbridge.model.RestPackBatteryData;
import com.gary.mqtthttpbridge.service.BatteryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Service
public class ImplBatteryService implements BatteryService {
    @Resource
    private BatteryMapper batteryMapper;

    @Override
    public Object getLastBattery() {
        return batteryMapper.selectOneByTime();
    }

    @Override
    public Object postOneBattery(Battery newBattery) {
        return batteryMapper.insert(newBattery);
    }

    @Override
    public RestPackBatteryData getBatteryByPack(int packNum) {
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
