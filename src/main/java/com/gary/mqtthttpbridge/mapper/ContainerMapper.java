package com.gary.mqtthttpbridge.mapper;

import com.gary.mqtthttpbridge.model.Container;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface ContainerMapper {
    int insert(Container record);

    int insertSelective(Container record);

    Container selectLastContainerByTime();
}