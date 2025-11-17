package com.gary.mqtthttpbridge.service;

import com.gary.mqtthttpbridge.model.Container;

public interface ContainerService {
    Object getLastContainer();

    int insertOne(Container container);
}
