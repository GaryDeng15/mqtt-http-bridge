package com.gary.mqtthttpbridge.service.impl;

import com.gary.mqtthttpbridge.mapper.ContainerMapper;
import com.gary.mqtthttpbridge.model.Container;
import com.gary.mqtthttpbridge.service.ContainerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ImplContainerService implements ContainerService {
    @Resource
    private ContainerMapper containerMapper;


    @Override
    public Object getLastContainer() {
        return containerMapper.selectLastContainerByTime();
    }

    @Override
    public int insertOne(Container container) {
        return containerMapper.insert(container);
    }
}
