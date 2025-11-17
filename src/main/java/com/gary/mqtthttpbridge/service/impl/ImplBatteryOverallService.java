package com.gary.mqtthttpbridge.service.impl;

import com.gary.mqtthttpbridge.mapper.BatteryOverallMapper;
import com.gary.mqtthttpbridge.model.BatteryOverall;
import com.gary.mqtthttpbridge.service.BatteryOverallService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ImplBatteryOverallService implements BatteryOverallService {
    @Resource
    private BatteryOverallMapper batteryOverallMapper;
    @Override
    public Object getLastBatteryOverall() {
        return batteryOverallMapper.selectOneByTime();
    }

    @Override
    public Integer postOneBatOverall(BatteryOverall newBatOverall) {
        return batteryOverallMapper.insert(newBatOverall);
    }
}
