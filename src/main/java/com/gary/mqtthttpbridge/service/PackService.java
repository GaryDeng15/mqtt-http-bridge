package com.gary.mqtthttpbridge.service;

import com.gary.mqtthttpbridge.model.Pack;
import com.gary.mqtthttpbridge.model.RestPackAll;

public interface PackService {
    RestPackAll getPackAll();

    Object getLastPack();

    Integer postOnePack(Pack newPack);
}
