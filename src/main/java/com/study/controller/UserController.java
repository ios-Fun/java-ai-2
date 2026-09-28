package com.study.controller;

import com.study.mapper.BaseMapper;
import com.study.tools.CommonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
     *
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

    @PostMapping("/getPathByNodeId")
    public List<String> getPathByNodeId(@RequestParam Integer nodeId) {
        List<String> flatList = baseMapper.getPathByNodeId(nodeId);
        return flatList;
    }

    @PostMapping("/getSubSystemIdByTTS")
    public Integer getSubSystemIdByTTS(@RequestParam(required = false) Integer tagId, @RequestParam(required = false) String tagName, @RequestParam(required = false) String srcTagName) {
        return baseMapper.getSubSystemIdByTTS(tagId, tagName, srcTagName);
    }

    @PostMapping("/getInstanceList")
    public List<Map> getInstanceList(){
        return baseMapper.getInstanceList();
    }

    @PostMapping("/getAllTags")
    public List<Map> getAllTags(@RequestParam String type, @RequestParam String parentName, @RequestParam(required = false) String tagType) {
        return baseMapper.getAllTags(type, parentName, tagType);
    }

    @PostMapping("/selectEnvironmentalExamplesByFuzzyMatching")
    public List<Map> selectEnvironmentalExamplesByFuzzyMatching(@RequestParam(required = false) String fuzzyName,
                                                                @RequestParam(required = false) Integer id,
                                                                @RequestParam(required = false) String tagName) {
        return baseMapper.selectEnvironmentalExamplesByFuzzyMatching(id, tagName, fuzzyName);
    }

    @PostMapping("/getLoadRateIndicatorByUnitId")
    public Map getLoadRateIndicatorByUnitId(@RequestParam(required = false) Integer unitId) {
        return baseMapper.getLoadRateIndicatorByUnitId(unitId);
    }

    @PostMapping("/getDefectModeByTagList")
    public List getDefectModeByTagList(@RequestBody List<Long> tagList) {
        return baseMapper.getDefectModeByTagList(tagList);
    }

    @PostMapping("/getBasicTagListByShadowFeatureId")
    public List<Map> getBasicTagListByShadowFeatureId(@RequestBody Long shadowFeatureId) {
        return baseMapper.getBasicTagListByShadowFeatureId(shadowFeatureId);
    }

    @PostMapping("/getAllGraph")
    List<Map> getAllGraph(@RequestParam String deviceName){
        return baseMapper.getAllGraph(deviceName);
    }

    @PostMapping("/getAssetInfos")
    public List<Map> getAssetInfos(@RequestBody Map tagIds) {
        return baseMapper.getAssetInfos(tagIds);
    }
    @PostMapping("/getUnitList")
    public List<Map> getUnitList() {
        return baseMapper.getUnitList();
    }
    @PostMapping("/getIndicators")
    public List<Map> getIndicators(Integer unitId) {
        return baseMapper.getIndicators(unitId);
    }

    @PostMapping("/getAllEvent")
    public List<Map> getAllEvent(@RequestParam Integer nodeId) {
        return baseMapper.getAllEvent(nodeId);
    }
    @PostMapping("/getAllEventList")
    public List<Map> getAllEventList(@RequestParam Integer unitId , @RequestParam Integer eventId) {
        return baseMapper.getAllEventList(unitId , eventId);
    }
    @PostMapping("/getNode")
    public  List<Map> getNode(@RequestParam Integer nodeId) {
        return baseMapper.getNode(nodeId);
    }

    private static final Set<String> NOISE = new HashSet<>(Arrays.asList(
            "运行状况","运行情况","运行状态","运行怎","情况","状态","如何","怎么样","怎样",
            "最近","今天","昨天","健康","评估","有没有","问题","是否","正常","时间",
            "想","下","看","查","一下","趋势","历史","曲线","数据","值","的"
    ));

    @PostMapping("/getSimilarityBenchmarkList")
    public Map<String, Object> getSimilarityBenchmarkList(@RequestParam String userMessage) {
        for (String w : NOISE)
            userMessage = userMessage.replace(w, "");
        List<Map> benchmarkInstanceList = baseMapper.getBenchmarkInstanceList();
        List<Map> result = benchmarkInstanceList.stream().map(row -> {
            Map<String, Object> flatMap = new LinkedHashMap<>();

            // 1. 先放入外层的 id 和 labels
            flatMap.put("id", row.get("id"));
            flatMap.put("labels", row.get("labels"));

            // 2. 将 properties 内的所有键值对提取到外层
            Object props = row.get("properties");
            if (props instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> propMap = (Map<String, Object>) props;
                flatMap.putAll(propMap);
            }

            return flatMap;
        }).collect(Collectors.toList());
        List<String> resColumn = new ArrayList<>();
        resColumn.add("id");resColumn.add("名称");resColumn.add("编码");
        List<Map> bestMatchingStr = CommonTool.getBestMatchingStr(result, userMessage, 0, "标杆", "labels", "名称", resColumn);
        if (bestMatchingStr != null && !bestMatchingStr.isEmpty()) {
            Map bestMatch = bestMatchingStr.get(0);
            Long id = Long.valueOf(bestMatch.get("id").toString());
            List<Map<String, Object>> rawList = baseMapper.getBenchmarkDetail(id);

            Map<String, Object> resMap = new HashMap<>();
            resMap.put("JAVAResult", rawList);
            resMap.put("bestMatch", bestMatch);
            return resMap;
        }
        return new HashMap<>();
    }
}
