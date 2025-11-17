package com.gary.mqtthttpbridge.mapper;

import com.gary.mqtthttpbridge.model.BatteryOverall;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface BatteryOverallMapper {
    int insert(BatteryOverall record);

    int insertSelective(BatteryOverall record);

    BatteryOverall selectOneByTime();
}