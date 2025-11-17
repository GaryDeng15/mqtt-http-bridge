package com.gary.mqtthttpbridge.mapper;

import com.gary.mqtthttpbridge.model.Battery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface BatteryMapper {
    int insert(Battery record);

    int insertSelective(Battery record);

    Battery selectOneByTime();

    /**
     * 根据pack号查询该组下所有电芯的最新数据
     * @param packNum pack号（1-8）
     * @return 该pack下14个电芯的最新数据列表
     */
    List<Battery> selectLatestByPack(@Param("packNum") Integer packNum);
}