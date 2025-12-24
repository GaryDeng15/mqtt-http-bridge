package com.gary.mqtthttpbridge.service.impl;

import com.alibaba.fastjson.JSON;
import com.gary.mqtthttpbridge.mapper.BatteryOverallMapper;
import com.gary.mqtthttpbridge.model.BatteryOverall;
import com.gary.mqtthttpbridge.service.BatteryOverallService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static com.gary.mqtthttpbridge.commons.Constant.REDIS_KEY_BATTERY_OVERALL_LATEST;

/**
 *
 */
@Service
public class ImplBatteryOverallService implements BatteryOverallService {
    @Resource
    private BatteryOverallMapper batteryOverallMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object getLastBatteryOverall() {

        // 1. 模糊匹配所有BatteryOverall的Redis Key
        //Set<String> keys = redisTemplate.keys(REDIS_KEY_BATTERY_OVERALL_LATEST + "*");
//        if (keys == null || keys.isEmpty()) {
//            return null;
//        }

        // 2. 按Key中的时间戳倒序排序，取最新的一条
        /*String latestKey = keys.stream()
                .sorted((k1, k2) -> {
                    long time1 = Long.parseLong(k1.split("_")[0].replace(REDIS_KEY_BATTERY_OVERALL_LATEST, ""));
                    long time2 = Long.parseLong(k2.split("_")[0].replace(REDIS_KEY_BATTERY_OVERALL_LATEST, ""));
                    return Long.compare(time2, time1); // 倒序
                })
                .findFirst()
                .orElse(null);

        if (latestKey == null) {
            return null;
        }*/

        // 3. 从Redis获取数据并转换为对象
        Map<Object, Object> hash = redisTemplate.opsForHash().entries(REDIS_KEY_BATTERY_OVERALL_LATEST);
        return JSON.parseObject(JSON.toJSONString(hash), BatteryOverall.class);

        //return batteryOverallMapper.selectOneByTime();
    }

    @Override
    public Integer postOneBatOverall(BatteryOverall newBatOverall) {
        return batteryOverallMapper.insert(newBatOverall);
    }
}
