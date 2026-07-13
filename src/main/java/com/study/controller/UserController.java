package com.study.controller;

import com.study.mapper.BaseMapper;
import com.study.model.UserModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/graph")
public class UserController {
    @Autowired
    BaseMapper baseMapper;


    @PostMapping("/tags")
    public List<Map> getTags(@RequestParam Integer nodeId) {
        //
        List<Map> list = baseMapper.selectTagByAttr(Long.valueOf(nodeId));
        return list;
    }

    /**
     * 根据实例名称获取设备或机组链式关系
     * @param assetName 设备名称
     * @param unitName  机组名称
     * @return
     */
    @PostMapping("/unitsOrAssets")
    public List<Map> getUnitsOrAssetsProps(@RequestParam(required = false) String assetName,
                               @RequestParam(required = false) String unitName) {
        //
        List<Map> list = baseMapper.selectUnitsOrAssetsPropsByInstanceName(assetName, unitName);
        return list;
    }


    @PostMapping("/getItems")
    public List<Map> getItems(@RequestParam Integer unitId, @RequestParam String type) {
        List<Map> list = baseMapper.getItems(unitId, type);
        return list.stream()
                .map(m -> (Map) m.get("item"))
                .collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/getTagInfosByName")
    public List<Map> getTagInfosByName(@RequestParam String name) {
        List<Map> list = baseMapper.getTagInfosByName(name);
        return list.stream()
                .map(m -> (Map) m.get("item"))
                .collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/getTagInfosByTTS")
    public List<Map> getTagInfosByTTS(@RequestParam(required = false) Integer tagId, @RequestParam(required = false) String tagName, @RequestParam(required = false) String srcTagName) {
        List<Map> list = baseMapper.getTagInfosByTTS(tagId, tagName, srcTagName);
        return list.stream()
                .map(m -> (Map) m.get("item"))
                .collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/getTagPathsByTTS")
    public List<Map> getTagPathsByTTS(@RequestParam(required = false) Integer tagId, @RequestParam(required = false) String tagName, @RequestParam(required = false) String srcTagName) {
        List<Map> flatList = baseMapper.getTagPathsByTTS(tagId, tagName, srcTagName);
        return flatList;
    }

    @PostMapping("/getSubSystemIdByTTS")
    public Integer getSubSystemIdByTTS(@RequestParam(required = false) Integer tagId, @RequestParam(required = false) String tagName, @RequestParam(required = false) String srcTagName){
        return baseMapper.getSubSystemIdByTTS(tagId, tagName, srcTagName);
    }

    @PostMapping("/getInstanceList")
    public List<Map> getInstanceList(){
        return baseMapper.getInstanceList();
    }
}
