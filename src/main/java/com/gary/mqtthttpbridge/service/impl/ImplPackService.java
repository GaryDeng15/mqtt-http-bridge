package com.gary.mqtthttpbridge.service.impl;

import com.gary.mqtthttpbridge.mapper.PackMapper;
import com.gary.mqtthttpbridge.model.Pack;
import com.gary.mqtthttpbridge.service.PackService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ImplPackService implements PackService {

    @Resource
    private PackMapper packMapper;

    @Override
    public Object getLastPack() {
        return packMapper.selectLastPack();
    }
}
