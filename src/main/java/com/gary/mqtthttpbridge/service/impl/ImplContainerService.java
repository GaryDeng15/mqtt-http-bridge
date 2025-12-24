package com.gary.mqtthttpbridge.service.impl;

import com.alibaba.fastjson.JSON;
import com.gary.mqtthttpbridge.mapper.ContainerMapper;
import com.gary.mqtthttpbridge.model.BatteryOverall;
import com.gary.mqtthttpbridge.model.Container;
import com.gary.mqtthttpbridge.service.ContainerService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.gary.mqtthttpbridge.commons.Constant.REDIS_KEY_BATTERY_OVERALL_LATEST;
import static com.gary.mqtthttpbridge.commons.Constant.REDIS_KEY_CONTAINER_LATEST;

/**
 *
 */
@Service
public class ImplContainerService implements ContainerService {
    @Resource
    private ContainerMapper containerMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Object getLastContainer() {
        //return containerMapper.selectLastContainerByTime();

        Map<Object, Object> hash = redisTemplate.opsForHash().entries(REDIS_KEY_CONTAINER_LATEST);
        return JSON.parseObject(JSON.toJSONString(hash), Container.class);
    }

    @Override
    public int insertOne(Container container) {
        return containerMapper.insert(container);
    }
}
