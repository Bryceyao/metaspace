package com.bryce.metaspace.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;
import com.bryce.metaspace.api.service.MetaspaceService;
import com.bryce.metaspace.constants.Urls;


/**
 * 提供基础数据服务.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-09.
 */
@Controller
@RequestMapping(value = Urls.V1.ROOT)
public class MetaspaceController {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MetaspaceService metaspaceService;

    @RequestMapping(value = Urls.V1.Render.GET_TYPES, method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public CommonResponse<List<MetaSpaceTypeDTO>> getTypes(String typeCodes) {
        LOG.info("MetaspaceController getTypes typeCodes={}", typeCodes);
        CommonResponse<List<MetaSpaceTypeDTO>> response = metaspaceService.getTypes(typeCodes);
        return response;
    }

    @RequestMapping(value = Urls.V1.Render.GET_TYPE_PROPERTYS, method =  {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public CommonResponse<List<MetaSpacePropertyDTO>> getTypePropertys(String typeCode) {
        LOG.info("MetaspaceController getTypePropertys typeCode={}", typeCode);
        CommonResponse<List<MetaSpacePropertyDTO>> response = metaspaceService.getTypePropertys(typeCode);
        return response;
    }

    @RequestMapping(value = Urls.V1.Render.GET_TYPE_ALL_CONTENTS, method =  {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public CommonResponse<JSONArray> getTypeAllContents(String typeCode) {
        LOG.info("MetaspaceController getTypeAllContents typeCode={}", typeCode);
        CommonResponse<JSONArray> response = metaspaceService.getTypeAllContents(typeCode);
        return response;
    }

    @RequestMapping(value = Urls.V1.Render.GET_TYPES_ALL_CONTENTS, method =  {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public CommonResponse<JSONObject> getTypesAllContents(String typeCodes) {
        LOG.info("MetaspaceController getTypeAllContents typeCode={}", typeCodes);
        CommonResponse<JSONObject> response = metaspaceService.getTypesAllContents(typeCodes);
        return response;
    }

    @RequestMapping(value = Urls.V1.Render.GET_TYPE_CONTENT, method =  {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public CommonResponse<JSONObject> getTypeContent(String typeCode, String propertyCode, String propertyContent) {
        LOG.info("MetaspaceController getTypeContent typeCode={},propertyCode={},propertyContent={}", typeCode,
                propertyCode, propertyContent);
        CommonResponse<JSONObject> response = metaspaceService.getTypeContent(typeCode, propertyCode, propertyContent);
        return response;
    }

    @RequestMapping(value = Urls.V1.Render.GET_TYPE_CONTENTS, method =  {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public CommonResponse<JSONArray> getTypeContents(String typeCode, String propertyCode, String propertyContent) {
        LOG.info("MetaspaceController getTypeContents typeCode={},propertyCode={},propertyContent={}", typeCode,
                propertyCode, propertyContent);
        CommonResponse<JSONArray> response = metaspaceService.getTypeContents(typeCode, propertyCode, propertyContent);
        return response;
    }

    @RequestMapping(value = Urls.V1.Render.GET_TYPE_DISTINCT_CONTENTS, method =  {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public CommonResponse<JSONArray> getTypeDistinctContents(String typeCode, String disPropertyCode, String propertyCode,
            String propertyContent, String flag) {
        LOG.info(
                "MetaspaceController getTypeContents typeCode={},disPropertyCode={},propertyCode={},propertyContent={}",
                typeCode, disPropertyCode, propertyCode, propertyContent);
        CommonResponse<JSONArray> response =
                metaspaceService.getTypeDistinctContents(typeCode, disPropertyCode, propertyCode, propertyContent);
        return response;
    }

    @RequestMapping(value = Urls.V1.Render.GET_AREAS_PCD, method =  {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public CommonResponse<JSONArray> getAreasPCD(String cKey, String nKey, String childKey, String propertyContent,
            String flag) {
        LOG.info("MetaspaceController getAreasPCD cKey={},nKey={},childKey={}", cKey, nKey, childKey);
        CommonResponse<JSONArray> response = metaspaceService.getAreasPCD(cKey, nKey, childKey);
        return response;
    }
}
