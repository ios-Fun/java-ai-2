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
    List<Map> selectUnitsOrAssetsPropsByInstanceName(@Param("assetName") String assetName, @Param("unitName") String unitName);


    List<Map> getItems(@Param("unitId")Integer unitId,@Param("type") String type);

    List<Map> getTagInfosByName(@Param("name") String name);

    List<Map> getTagInfosByTTS(@Param("tagId")Integer tagId,@Param("tagName") String tagName,@Param("srcTagName") String srcTagName);

    List<Map> getTagPathsByTTS(@Param("tagId")Integer tagId,@Param("tagName") String tagName,@Param("srcTagName") String srcTagName);

    Integer getSubSystemIdByTTS(@Param("tagId")Integer tagId,@Param("tagName") String tagName,@Param("srcTagName") String srcTagName);

    List<Map> getInstanceList();
}
