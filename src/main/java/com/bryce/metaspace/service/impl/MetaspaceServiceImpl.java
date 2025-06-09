package com.bryce.metaspace.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.common.ResponseCodeTypeEnum;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;
import com.bryce.metaspace.api.enums.MetaspaceResponseCodeTypeEnum;
import com.bryce.metaspace.api.service.MetaspaceService;
import com.bryce.metaspace.constants.ConfigConsts;
import com.bryce.metaspace.infrastructure.MetaSpaceCacheHandle;
import com.bryce.metaspace.mapper.MetaSpaceMapper;

/**
 * 字典查询服务实现.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-09.
 */
@Service
public class MetaspaceServiceImpl implements MetaspaceService {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    public MetaSpaceCacheHandle mapCacheHandle;
    
    @Resource
    MetaSpaceMapper metaspaceMapper;

    /**
     * 根据类型code获得字典类型集合
     */
    @Override
    public CommonResponse<List<MetaSpaceTypeDTO>> getTypes(String... typeCodes) {
        List<MetaSpaceTypeDTO> metaSpaceTypeDTOs = new ArrayList<>();
        try {
            if (StringUtils.isEmpty(typeCodes)||(typeCodes.length==1&&StringUtils.isEmpty(typeCodes[0]))) {
                metaSpaceTypeDTOs.addAll(mapCacheHandle.getTypes());
            } else {
                for (int i = 0; i < typeCodes.length; i++) {
                    if (!StringUtils.isEmpty(typeCodes[i])) {
                        MetaSpaceTypeDTO metaSpaceTypeDTO = mapCacheHandle.getType(typeCodes[i]);
                        if (metaSpaceTypeDTO != null) {
                            metaSpaceTypeDTOs.add(metaSpaceTypeDTO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("MetaspaceServiceImpl getTypes error ", e);
            CommonResponse<List<MetaSpaceTypeDTO>> CommonResponse =
                    new CommonResponse<List<MetaSpaceTypeDTO>>(MetaspaceResponseCodeTypeEnum.FAIL_BUSI.getCode());
            CommonResponse.setMessage(CommonResponse.getMessage() + e.getMessage());
            return CommonResponse;
        }
        return new CommonResponse<List<MetaSpaceTypeDTO>>(ResponseCodeTypeEnum.SUCCESS.getCode(), metaSpaceTypeDTOs);
    }



    /**
     * 查询字典属性.
     */
    @Override
    public CommonResponse<List<MetaSpacePropertyDTO>> getTypePropertys(String typeCode) {
        if (StringUtils.isEmpty(typeCode)) {
            return new CommonResponse<List<MetaSpacePropertyDTO>>(MetaspaceResponseCodeTypeEnum.REJECT_TYPE_CODE.getCode());
        }
        if (mapCacheHandle.getType(typeCode) == null) {
            return new CommonResponse<List<MetaSpacePropertyDTO>>(MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());
        }

        List<MetaSpacePropertyDTO> metaSpacePropertyDTOs = mapCacheHandle.getPropertys(typeCode);
        return new CommonResponse<List<MetaSpacePropertyDTO>>(ResponseCodeTypeEnum.SUCCESS.getCode(), metaSpacePropertyDTOs);
    }


    /**
     * 查询字典所有枚举对象列表.
     */
    @Override
    public CommonResponse<JSONArray> getTypeAllContents(String typeCode) {
        if (StringUtils.isEmpty(typeCode)) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.REJECT_TYPE_CODE.getCode());
        }
        if (mapCacheHandle.getType(typeCode) == null) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());
        }

        JSONArray datas = mapCacheHandle.getContents(typeCode);
        return new CommonResponse<JSONArray>(ResponseCodeTypeEnum.SUCCESS.getCode(), datas);
    }

    /**
     * 查询多个字典所有枚举对象列表.
     */
    @Override
    public CommonResponse<JSONObject> getTypesAllContents(String typeCodes) {
        if (StringUtils.isEmpty(typeCodes)) {
            return new CommonResponse<JSONObject>(MetaspaceResponseCodeTypeEnum.REJECT_TYPE_CODES.getCode());
        }
        JSONObject rtn=new JSONObject();
        String[] typeCodesArr=typeCodes.split(",");
        for (int i = 0; i < typeCodesArr.length; i++) {
            JSONArray datas = mapCacheHandle.getContents(typeCodesArr[i]);
            rtn.put(typeCodesArr[i], datas);
        }
        return new CommonResponse<JSONObject>(ResponseCodeTypeEnum.SUCCESS.getCode(), rtn);
    }
    

    /**
     * 查询字典单个枚举对象. 例如查询行政区划字典 城市编码为440300即深圳市的数据，实际有多个区，只随机返回一条数据、如罗湖区数据
     */
    @Override
    public CommonResponse<JSONObject> getTypeContent(String typeCode, String propertyCode, String propertyContent) {
        if (StringUtils.isEmpty(typeCode)) {
            return new CommonResponse<JSONObject>(MetaspaceResponseCodeTypeEnum.REJECT_TYPE_CODE.getCode());
        }
        if (StringUtils.isEmpty(propertyCode)) {
            return new CommonResponse<JSONObject>(MetaspaceResponseCodeTypeEnum.REJECT_PROPERTY_CODE.getCode());
        }
        if (StringUtils.isEmpty(propertyContent)) {
            return new CommonResponse<JSONObject>(MetaspaceResponseCodeTypeEnum.REJECT_CONTENT.getCode());
        }
        if (mapCacheHandle.getType(typeCode) == null) {
            return new CommonResponse<JSONObject>(MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());
        }
        JSONArray contents = getContents(typeCode, propertyCode, propertyContent);
        for (int i = 0; i < contents.size(); i++) {
            JSONObject json = contents.getJSONObject(i);
            // 返回有效状态的数据
            if (ConfigConsts.CONTENT_STATE_TRUE.toString().equals(json.getString(MetaSpaceCacheHandle.VALID_STATE))) {
                return new CommonResponse<JSONObject>(ResponseCodeTypeEnum.SUCCESS.getCode(), json);
            }
            // 数据无效且是最后一行数据，返回数据和警告
            else if (i == contents.size() - 1) {
                return new CommonResponse<JSONObject>(MetaspaceResponseCodeTypeEnum.WARN_STATE.getCode(), json);
            }
        }
        return new CommonResponse<JSONObject>(MetaspaceResponseCodeTypeEnum.FAIL_CONTENT.getCode());
    }

    /**
     * 查询字典某类枚举对象. 例如查询行政区划字典 城市编码为440300即深圳市的数据，返回罗湖区、福田区、南山区、宝安区、龙岗区、盐田区六条数据。
     */
    @Override
    public CommonResponse<JSONArray> getTypeContents(String typeCode, String propertyCode, String propertyContent) {
        LOG.info("MetaspaceServiceImpl getTypeContents typeCode={},propertyCode={},propertyContent={}", typeCode,
                propertyCode, propertyContent);
        if (StringUtils.isEmpty(typeCode)) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.REJECT_TYPE_CODE.getCode());
        }
        if (StringUtils.isEmpty(propertyCode)) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.REJECT_PROPERTY_CODE.getCode());
        }
        if (StringUtils.isEmpty(propertyContent)) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.REJECT_CONTENT.getCode());
        }
        if (mapCacheHandle.getType(typeCode) == null) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());
        }
        String[] propertyContentsz = propertyContent.split(",");
        // 单个属性值查询
        if (propertyContentsz.length == 1) {
            JSONArray contents = getContents(typeCode, propertyCode, propertyContent);
            for (int i = 0; i < contents.size(); i++) {
                JSONObject json = contents.getJSONObject(i);
                // 有效状态的数据
                if (ConfigConsts.CONTENT_STATE_TRUE.toString().equals(json.getString(MetaSpaceCacheHandle.VALID_STATE))) {
                    continue;
                }
                // 数据无效且是最后一行数据，返回数据和警告
                if (i == contents.size() - 1) {
                    return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.WARN_STATE.getCode(), contents);
                }
                // 数据无效且有多行数据，去处无效数据
                else {
                    contents.remove(i);
                    i--;
                }
            }
            return new CommonResponse<JSONArray>(ResponseCodeTypeEnum.SUCCESS.getCode(), contents);
        }
        // 多个属性值查询
        else {
            JSONArray results = new JSONArray();

            for (int i = 0; i < propertyContentsz.length; i++) {
                JSONArray contents = getContents(typeCode, propertyCode, propertyContentsz[i]);
                for (int j = 0; j < contents.size(); j++) {
                    JSONObject json = contents.getJSONObject(j);
                    // 有效状态的数据
                    if (ConfigConsts.CONTENT_STATE_TRUE.toString().equals(json.getString(MetaSpaceCacheHandle.VALID_STATE))) {
                        results.add(json);
                    }
                }
            }
            return new CommonResponse<JSONArray>(ResponseCodeTypeEnum.SUCCESS.getCode(), results);
        }
    }

    private JSONArray getContents(String typeCode, String propertyCode, String propertyContent) {
        JSONArray contents = mapCacheHandle.getContents(typeCode);
        JSONArray results = new JSONArray();
        for (int i = 0; i < contents.size(); i++) {
            JSONObject json = contents.getJSONObject(i);
            if (propertyContent.equals(json.getString(propertyCode))) {
                results.add(json);
            }
        }
        return results;
    }


    @Override
    public CommonResponse<JSONArray> getTypeDistinctContents(String typeCode, String disPropertyCode, String propertyCode,
            String propertyContent) {
        LOG.info(
                "MetaspaceServiceImpl getTypeDistinctContents typeCode={},disPropertyCode={},propertyCode={},propertyContent={}",
                typeCode, disPropertyCode, propertyCode, propertyContent);
        if (StringUtils.isEmpty(typeCode)) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.REJECT_TYPE_CODE.getCode());
        }
        if (StringUtils.isEmpty(disPropertyCode)) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.REJECT_DIS_PROPERTY.getCode());
        }
        if (mapCacheHandle.getType(typeCode) == null) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());
        }
        List<MetaSpacePropertyDTO> propertys = mapCacheHandle.getPropertys(typeCode);
        if (CollectionUtils.isEmpty(propertys)) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.FAIL_PROPERTY.getCode());
        }
        boolean notFlag = true;
        for (int i = 0; i < propertys.size(); i++) {
            if (disPropertyCode.equals(propertys.get(i).getPropertyCode())) {
                notFlag = false;
                break;
            }
        }
        if (notFlag) {
            return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.REJECT_NOT_DIS_PROPERTY.getCode());
        }

        

        JSONArray results = new JSONArray();
        if (StringUtils.isEmpty(propertyCode) || StringUtils.isEmpty(propertyContent)) {
            results=mapCacheHandle.getContents(typeCode);
        }
        else{
            String[] propertyContentsz = propertyContent.split(",");
         // 单个属性值查询
            if (propertyContentsz.length == 1) {
                results = getContents(typeCode, propertyCode, propertyContent);
                for (int i = 0; i < results.size(); i++) {
                    JSONObject json = results.getJSONObject(i);
                    // 有效状态的数据
                    if (ConfigConsts.CONTENT_STATE_TRUE.toString().equals(json.getString(MetaSpaceCacheHandle.VALID_STATE))) {
                        continue;
                    }
                    // 数据无效且是最后一行数据，返回数据和警告
                    if (i == results.size() - 1) {
                        return new CommonResponse<JSONArray>(MetaspaceResponseCodeTypeEnum.WARN_STATE.getCode(), results);
                    }
                    // 数据无效且有多行数据，去处无效数据
                    else {
                        results.remove(i);
                        i--;
                    }
                }
            }
            // 多个属性值查询
            else {
                for (int i = 0; i < propertyContentsz.length; i++) {
                    JSONArray contents = getContents(typeCode, propertyCode, propertyContentsz[i]);
                    for (int j = 0; j < contents.size(); j++) {
                        JSONObject json = contents.getJSONObject(j);
                        // 有效状态的数据
                        if (ConfigConsts.CONTENT_STATE_TRUE.toString().equals(json.getString(MetaSpaceCacheHandle.VALID_STATE))) {
                            results.add(json);
                        }
                    }
                }
                return new CommonResponse<JSONArray>(ResponseCodeTypeEnum.SUCCESS.getCode(), results);
            }
        }
        

        // Distinct去重
        JSONArray arr = distinct(results, disPropertyCode);

        return new CommonResponse<JSONArray>(ResponseCodeTypeEnum.SUCCESS.getCode(), arr);
    }

    /** 高效去重算法 3000数据全部比对仅需1毫秒 */
    private JSONArray distinct(JSONArray datas, String disPropertyCode) {
        Map<String, JSONObject> map = new HashMap<>();
        for (int i = 0; i < datas.size(); i++) {
            JSONObject json = datas.getJSONObject(i);
            map.put(json.getString(disPropertyCode), json);
        }
        Set<Entry<String, JSONObject>> set = map.entrySet();
        JSONArray result = new JSONArray();
        for (Entry<String, JSONObject> entry : set) {
            result.add(entry.getValue());
        }
        return result;
    }
    
    /** 返回级联省市区数据 */
    public CommonResponse<JSONArray> getAreasPCD(String cKey,String nKey,String childKey){
        if(StringUtils.isEmpty(cKey)){
            cKey="code";
        }
        if(StringUtils.isEmpty(nKey)){
            nKey="name";
        }
        if(StringUtils.isEmpty(childKey)){
            childKey="child";
        }
        String areas="areas";
        String provinceCode="provinceCode";
        String provinceName="provinceName";
        String cityCode="cityCode"; 
        String cityName="cityName";
        String districtCode="districtCode"; 
        String districtName="districtName";
        
        JSONArray results=new JSONArray();
        Map<String,JSONObject> mapProvince=new HashMap<>();
        Map<String, JSONObject> mapCity = new HashMap<>();
        JSONArray arr=mapCacheHandle.getContents(areas);
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = arr.getJSONObject(i);
            String codeP=json.getString(provinceCode);
            JSONObject province=mapProvince.get(codeP);
            if(province==null){
                province=buildAreaJson(cKey, nKey, codeP, json.getString(provinceName),childKey);
                mapProvince.put(codeP, province);
                results.add(province);
            }
            
            String codeC=json.getString(cityCode);
            JSONObject city=mapCity.get(codeC);
            if(city==null){
                city=buildAreaJson(cKey, nKey, codeC, json.getString(cityName),childKey);
                mapCity.put(codeC, city);
                province.getJSONArray(childKey).add(city);
            }
            
            JSONObject district = buildAreaJson(cKey, nKey, json.getString(districtCode), json.getString(districtName),null);
            city.getJSONArray(childKey).add(district);
        }
        return new CommonResponse<JSONArray>(ResponseCodeTypeEnum.SUCCESS.getCode(), results);
    }
    
    private JSONObject buildAreaJson(String cKey,String nKey,String cValue,String nValue,String childKey){
        JSONObject json=new JSONObject();
        json.put(cKey, cValue);
        json.put(nKey, nValue);
        if(childKey!=null){
            json.put(childKey, new JSONArray());
        }
        return json;
    }

}
