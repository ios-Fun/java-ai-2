package com.study.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseMapper {
    List<Map> selectTagByAttr(@Param("attrId")Long attrId);

    /**
     * 根据设备名称模糊查询设备信息
     * @param assetName 设备名称
     * @return
     */
    List<Map> selectAssetPropByAssetName(@Param("assetName") String assetName);
}
