package com.study.controller;

import com.study.mapper.BaseMapper;
import com.study.model.UserModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/assets")
    public List<Map> getAssets(@RequestParam String assetName) {
        //
        List<Map> list = baseMapper.selectAssetPropByAssetName(assetName);
        return list;
    }
}
