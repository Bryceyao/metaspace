package com.bryce.metaspace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;
import com.bryce.metaspace.api.enums.MetaspaceResponseCodeTypeEnum;
import com.bryce.metaspace.api.service.MetaManageService;
import com.bryce.metaspace.constants.Urls;

/**
 * 基础数据服务管理.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-09.
 */
@Controller
@RequestMapping(value = Urls.V1.ROOT)
public class ManageController {
        
    
    @Autowired
    private MetaManageService manageService;

    @RequestMapping(value = Urls.V1.Manage.ADD_TYPE, method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse<String> addType( MetaSpaceTypeDTO metaSpaceTypeDTO) {
        CommonResponse<String> response = manageService.addType(metaSpaceTypeDTO);
        return response;
    }
    
    @RequestMapping(value = Urls.V1.Manage.ADD_UPDATE_PROPERTYS, method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse<String> addUpdatePropertys(String typeCode,String jsonArrayPropertys ) {
        List<MetaSpacePropertyDTO> metaSpacePropertyDTOs=null;
        try {
            metaSpacePropertyDTOs=JSONArray.parseArray(jsonArrayPropertys, MetaSpacePropertyDTO.class);
        } catch (Exception e) {
            CommonResponse<String> response= new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_JSON_PROPERTYS.getCode());
            response.setMessage(response.getMessage()+e.getMessage());
            return response;
        }
        CommonResponse<String> response = manageService.addUpdatePropertys(typeCode, metaSpacePropertyDTOs);
        return response;
    }
    
    @RequestMapping(value = Urls.V1.Manage.ADD_UPDATE_CONTENTS, method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse<String> addUpdateContents(String typeCode,String uProperty,String jsonArrayContents) {
        CommonResponse<String> response = manageService.addUpdateContents(typeCode, uProperty, jsonArrayContents);
        return response;
    }
}
