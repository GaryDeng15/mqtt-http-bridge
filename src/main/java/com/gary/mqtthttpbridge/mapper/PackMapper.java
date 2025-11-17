package com.gary.mqtthttpbridge.mapper;

import com.gary.mqtthttpbridge.model.Battery;
import com.gary.mqtthttpbridge.model.Container;
import com.gary.mqtthttpbridge.model.Pack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PackMapper {
    int insert(Pack record);

    int insertSelective(Pack record);

    Pack selectOneByTime();

    List<Pack> selectPackAll();
}
