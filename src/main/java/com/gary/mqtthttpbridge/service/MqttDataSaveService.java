package com.gary.mqtthttpbridge.service;

import com.alibaba.fastjson.JSONObject;

public interface MqttDataSaveService {
    void parseJSON(String payload);

    void saveData(String payload);
}
