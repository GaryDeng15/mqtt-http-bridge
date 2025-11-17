package com.gary.mqtthttpbridge.service;

import com.gary.mqtthttpbridge.model.BatteryOverall;

public interface BatteryOverallService {
    Object getLastBatteryOverall();

    Integer postOneBatOverall(BatteryOverall newBatOverall);
}
