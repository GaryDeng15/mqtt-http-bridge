package com.gary.mqtthttpbridge.mapper;

import com.gary.mqtthttpbridge.model.Pack;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface PackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pack record);

    int insertSelective(Pack record);

    Pack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pack record);

    int updateByPrimaryKey(Pack record);

    Pack selectLastPack();
}