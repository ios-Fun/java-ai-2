package com.study.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseMapper {
    List<Map> selectTagByAttr(@Param("attrId")Long attrId);
}
