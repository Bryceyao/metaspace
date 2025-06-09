package com.bryce.metaspace.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.common.ResponseCodeTypeEnum;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;
import com.bryce.metaspace.api.enums.MetaspaceResponseCodeTypeEnum;
import com.bryce.metaspace.api.service.MetaManageService;
import com.bryce.metaspace.bean.MetaSpaceContent;
import com.bryce.metaspace.bean.MetaSpaceProperty;
import com.bryce.metaspace.bean.MetaSpaceType;
import com.bryce.metaspace.constants.ConfigConsts;
import com.bryce.metaspace.infrastructure.IdWorker;
import com.bryce.metaspace.infrastructure.MetaSpaceCacheHandle;
import com.bryce.metaspace.mapper.MetaSpaceContentMapper;
import com.bryce.metaspace.mapper.MetaSpacePropertyMapper;
import com.bryce.metaspace.mapper.MetaSpaceTypeMapper;
import com.bryce.metaspace.mapper.MetaSpaceMapper;


/**
 * 字典管理服务实现
 * 
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-09.
 */
@Service
public class ManageServiceImpl extends ServiceImpl<MetaSpaceContentMapper, MetaSpaceContent> implements MetaManageService {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    public MetaSpaceCacheHandle mapCacheHandle;
    
    @Resource
    MetaSpaceMapper metaspaceMapper;
    
    @Resource
    MetaSpacePropertyMapper metaSpacePropertyMapper;

    /**
     * 新增字典类型.
     */
    @Override
    public CommonResponse<String> addType(MetaSpaceTypeDTO metaSpaceTypeDTO) {
        if (checkTypeExist(metaSpaceTypeDTO.getTypeCode())) {
            return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_EXIST_TYPE.getCode());
        }
        try {
            MetaSpaceType metaSpaceType = conversionType(metaSpaceTypeDTO);
            metaspaceMapper.insert(metaSpaceType);
        } catch (Exception e) {
            LOG.error("ManageServiceImpl addType fail e", e);
            return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_BUSI.getCode(),e.getMessage());
        }
        mapCacheHandle.setType(metaSpaceTypeDTO.getTypeCode());
        return new CommonResponse<String>(ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    /**
     * 新增修改字典属性.
     */
    @Override
    public CommonResponse<String> addUpdatePropertys(String typeCode, List<MetaSpacePropertyDTO> metaSpacePropertyDTOs) {
        if (!checkTypeExist(typeCode)) {
            return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());
        }
        try {
            // 要新增的字典属性
            List<MetaSpaceProperty> addList = new ArrayList<MetaSpaceProperty>();
            // 已存在的字典属性
            List<MetaSpaceProperty> existPropertys = metaspaceMapper.getTypePropertys(typeCode);
            /**
             * 该字典项无属性配置，默认全部新增
             */
            if (CollectionUtils.isEmpty(existPropertys)) {
                for (int i = 0; i < metaSpacePropertyDTOs.size(); i++) {
                    MetaSpaceProperty propertysAdd = conversionProperty(metaSpacePropertyDTOs.get(i));
                    propertysAdd.setTypeCode(typeCode);
                    addList.add(propertysAdd);
                }
            }
            /**
             * 比对属性code是否存在，再决定新增还是修改
             */
            else {
                for (int i = 0; i < metaSpacePropertyDTOs.size(); i++) {
                    MetaSpacePropertyDTO metaSpacePropertyDTO = metaSpacePropertyDTOs.get(i);
                    boolean existFlag = false;

                    for (int j = 0; j < existPropertys.size(); j++) {
                        /**
                         * 如果属性code存在，修改
                         */
                        if (metaSpacePropertyDTO.getPropertyCode().equals(existPropertys.get(j).getPropertyCode())) {
                            MetaSpaceProperty propertysUpdate =
                                    conversionProperty(existPropertys.get(j), metaSpacePropertyDTO);
                            metaSpacePropertyMapper.updateById(propertysUpdate);
                            existFlag = true;
                            existPropertys.remove(j);// 移除命中的存在项，减少后续操作轮询次数
                            break;
                        }
                    }
                    /**
                     * 如果属性code不存在，新增
                     */
                    if (!existFlag) {
                        MetaSpaceProperty propertysAdd = conversionProperty(metaSpacePropertyDTO);
                        propertysAdd.setTypeCode(typeCode);
//                        addList.add(propertysAdd);
                        metaSpacePropertyMapper.insert(propertysAdd);
                    }
                }
            }
            if(!CollectionUtils.isEmpty(addList)){
//                dao.saveBatch(addList);
            }
        } catch (Exception e) {
            LOG.error("ManageServiceImpl addType fail e", e);
            return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_BUSI.getCode(),e.getMessage());
        }
        mapCacheHandle.setPropertys(typeCode);
        return new CommonResponse<String>(ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    /**
     * 将PropertyDTO对象的name和desc重新赋值给PropertyPO对象，并更新PropertyPO对象的UpdatedAt
     */
    private MetaSpaceProperty conversionProperty(MetaSpaceProperty metaSpaceProperty,
            MetaSpacePropertyDTO metaSpacePropertyDTO) {
        metaSpaceProperty.setPropertyName(metaSpacePropertyDTO.getPropertyName());
        metaSpaceProperty.setPropertyDesc(metaSpacePropertyDTO.getPropertyDesc());
        metaSpaceProperty.setUpdatedAt(new Date());
        return metaSpaceProperty;
    }

    /**
     * 根据PropertyDTO对象新建PropertyPO对象
     */
    private MetaSpaceProperty conversionProperty(MetaSpacePropertyDTO metaSpacePropertyDTO) {
        MetaSpaceProperty metaSpaceProperty = new MetaSpaceProperty();
        metaSpaceProperty.setUpdatedAt(new Date());
        metaSpaceProperty.setCreatedAt(new Date());
        metaSpaceProperty.setPropertyCode(metaSpacePropertyDTO.getPropertyCode());
        metaSpaceProperty.setPropertyName(metaSpacePropertyDTO.getPropertyName());
        metaSpaceProperty.setPropertyDesc(metaSpacePropertyDTO.getPropertyDesc());
        return metaSpaceProperty;
    }

    /**
     * 根据TypeDTO对象新建TypePO对象
     */
    private MetaSpaceType conversionType(MetaSpaceTypeDTO metaSpaceTypeDTO) {
        MetaSpaceType metaSpaceType = new MetaSpaceType();
        metaSpaceType.setTypeCode(metaSpaceTypeDTO.getTypeCode());
        metaSpaceType.setTypeName(metaSpaceTypeDTO.getTypeName());
        metaSpaceType.setTypeDesc(metaSpaceTypeDTO.getTypeDesc());
        metaSpaceType.setCreatedAt(new Date());
        metaSpaceType.setUpdatedAt(new Date());
        return metaSpaceType;
    }


    private boolean checkTypeExist(String typeCode) {
        MetaSpaceType metaSpaceType = metaspaceMapper.getType(typeCode);
        if (metaSpaceType == null) {
            return false;
        }
        return true;
    }

    /**
     * 新增修改字典内容.
     *
     * @param typeCode 类型编码（参数举例：areas）
     * @param uProperty 字典唯一索引key，根据这个字段判断是新增还是修改（参数举例：districtCode）
     * @param jsonContents
     *        JSONArray格式的字典内容，key为字典属性。必须包涵uProperty，且属性唯一(参数举例：[{"cityCode":"440300","cityName":
     *        "深圳市", "districtCode":"440301","districtName":"市辖区","id":1931,"provinceCode":"440000",
     *        "provinceName":"广东省"},{"cityCode":"440300","cityName":"深圳市","districtCode":"440303",
     *        "districtName":"罗湖区","id":1932,"provinceCode":"440000","provinceName":"广东省"},{
     *        "cityCode":"440300","cityName":"深圳市","districtCode":"440304","districtName":"福田区","id"
     *        :1933,"provinceCode":"440000","provinceName":"广东省"},{"cityCode":"440300","cityName":
     *        "深圳市","districtCode":"440305","districtName":"南山区","id":1934,"provinceCode":"440000",
     *        "provinceName":"广东省"},{"cityCode":"440300","cityName":"深圳市","districtCode":"440306",
     *        "districtName":"宝安区","id":1935,"provinceCode":"440000","provinceName":"广东省"},{
     *        "cityCode":"440300","cityName":"深圳市","districtCode":"440307","districtName":"龙岗区","id"
     *        :1936,"provinceCode":"440000","provinceName":"广东省"},{"cityCode":"440300","cityName":
     *        "深圳市","districtCode":"440308","districtName":"盐田区","id":1937,"provinceCode":"440000",
     *        "provinceName":"广东省"}] )
     */
    @Override
    public CommonResponse<String> addUpdateContents(String typeCode, String uProperty,
            String jsonArrayContents) {
        if (!checkTypeExist(typeCode)) {
            return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());
        }
        // 为保证新增的数据propertyCode无误，从meta_space_property表里取值
        List<MetaSpaceProperty> metaSpacePropertys = metaspaceMapper.getTypePropertys(typeCode);
        if (CollectionUtils.isEmpty(metaSpacePropertys)) {
            return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_PROPERTY.getCode());
        }
        
        IdWorker idWorker = new IdWorker(9L);
        try {
            // 已存在的字典内容
            List<MetaSpaceContent> existContentJudges = metaspaceMapper.getTypeContents(typeCode);
            // 参数序列化
            JSONArray paramArray = JSONArray.parseArray(jsonArrayContents);
            //参数去重
            Map<String,JSONObject> paramMap=new HashMap<>();
            for (int i = 0; i < paramArray.size(); i++) {
                JSONObject json = paramArray.getJSONObject(i);
                String uk=json.getString(uProperty);
                if(StringUtils.isEmpty(uk)){
                    return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_BUSI.getCode(),"无法根据key值 ["+uProperty+"]从传入的参数中找到value值"); 
                }
                paramMap.put(uk, json);
            }
            // 要新增的字典内容
            List<MetaSpaceContent> addList = new ArrayList<MetaSpaceContent>();
            /**
             * 字典内容不存在，提交的内容全部新增
             */
            if (CollectionUtils.isEmpty(existContentJudges)) {
                for (Map.Entry<String,JSONObject> entry : paramMap.entrySet()) { 
                    Long rowId = idWorker.nextId();
                    JSONObject rowJson = entry.getValue();
                    for (int j = 0; j < metaSpacePropertys.size(); j++) {
                        String propertyCode = metaSpacePropertys.get(j).getPropertyCode();
                        String content=rowJson.getString(propertyCode);
                        if(StringUtils.isEmpty(content)){
                            return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_BUSI.getCode(),"无法根据key值 "+propertyCode+"从传入的参数中找到value值");
                        }
                        addList.add(buildContent(typeCode, propertyCode, content, rowId));
                    }
                }
            }
            /**
             * 字典有内容存在，比较属性为uProperty的内容，判断是新增还是修改
             */
            else {
                /**
                 * 判断uProperty是否为唯一索引，依据根据uProperty内容查询出来的数据 rowId相同
                 */
                int judgeNum = 0;
                for (int i = 0; i < existContentJudges.size(); i++) {
                    if (existContentJudges.get(i).getPropertyCode().equals(uProperty)) {
                        judgeNum++;
                        String uContent = existContentJudges.get(i).getContent();
                        List<MetaSpaceContent> rowContents =
                                metaspaceMapper.getTypeContents(typeCode, uProperty, uContent, true);
                        Long rowId = rowContents.get(0).getRowId();
                        for (int j = 1; j < rowContents.size(); j++) {
                            if (rowContents.get(j).getRowId().longValue() != rowId.longValue()) {
                                return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_BUSI.getCode(),"传入的uProperty不是字典项唯一索引key");
                            }
                        }
                        // 随机最多取五组值进行判断
                        if (judgeNum >= 5) {
                            break;
                        }
                    }
                }
                /**
                 * uProperty为字典项唯一索引，循环传入的参数
                 */
                for (Map.Entry<String,JSONObject> entry : paramMap.entrySet()) { 
                    JSONObject rowJson = entry.getValue();
                    String uContent = rowJson.getString(uProperty);
                    // 比对是否存在唯一约束相同的字典项
                    List<MetaSpaceContent> existContents =
                            metaspaceMapper.getTypeContents(typeCode, uProperty, uContent, true);
                    existContents.addAll(metaspaceMapper.getTypeContents(typeCode, uProperty, uContent, false));
                    /**
                     * 不存在的字典项，新增
                     */
                    if (CollectionUtils.isEmpty(existContents)) {
                        Long rowId = idWorker.nextId();
                        for (int j = 0; j < metaSpacePropertys.size(); j++) {
                            String propertyCode = metaSpacePropertys.get(j).getPropertyCode();
                            addList.add(buildContent(typeCode, propertyCode, rowJson.getString(propertyCode), rowId));
                        }
                    }
                    /**
                     * 已存在的字典项，修改
                     */
                    else {
                        String state =rowJson.getString("state");
                        //修改内容
                        if(StringUtils.isEmpty(state)||String.valueOf(ConfigConsts.CONTENT_STATE_TRUE).equals(state)){
                            for (int j = 0; j < existContents.size(); j++) {
                                MetaSpaceContent existContent = existContents.get(j);
                                existContent=buildContent(existContent, rowJson.getString(existContent.getPropertyCode()),ConfigConsts.CONTENT_STATE_TRUE);
                                baseMapper.updateById(existContent);
                            }
                        }
                        //修改为无效
                        else{
                            for (int j = 0; j < existContents.size(); j++) {
                                MetaSpaceContent existContent = existContents.get(j);
                                existContent=buildContent(existContent, existContent.getContent(),0);
                                baseMapper.updateById(existContent);
                            }
                        }
                    }
                }
            }
            if(!CollectionUtils.isEmpty(addList)){
                saveBatch(addList);
            }
        } catch (Exception e) {
            LOG.error("ManageServiceImpl addUpdateTypePropertyContents fail e", e);
            return new CommonResponse<String>(MetaspaceResponseCodeTypeEnum.FAIL_BUSI.getCode(),e.getMessage());
        }
        mapCacheHandle.setContents(typeCode);
        return new CommonResponse<String>(ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    private MetaSpaceContent buildContent(String typeCode, String propertyCode, String content, Long rowId) {
        MetaSpaceContent metaSpaceContent = new MetaSpaceContent();
        metaSpaceContent.setTypeCode(typeCode);
        metaSpaceContent.setPropertyCode(propertyCode);
        metaSpaceContent.setContent(content);
        metaSpaceContent.setState(ConfigConsts.CONTENT_STATE_TRUE);
        metaSpaceContent.setRowId(rowId);
        metaSpaceContent.setCreatedAt(new Date());
        metaSpaceContent.setUpdatedAt(new Date());
        return metaSpaceContent;
    }

    private MetaSpaceContent buildContent(MetaSpaceContent metaSpaceContent, String content, Integer state) {
        metaSpaceContent.setContent(content);
        metaSpaceContent.setState(state);
        metaSpaceContent.setUpdatedAt(new Date());
        return metaSpaceContent;
    }

}
