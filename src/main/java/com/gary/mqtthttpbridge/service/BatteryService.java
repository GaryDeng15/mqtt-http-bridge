package com.gary.mqtthttpbridge.service;

import com.gary.mqtthttpbridge.model.Battery;
import com.gary.mqtthttpbridge.model.RestPackBatteryData;

public interface BatteryService {
    Object getLastBattery();

    Object postOneBattery(Battery newBattery);

    RestPackBatteryData getBatteryByPack(int packNum);
}
