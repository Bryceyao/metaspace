package com.bryce.metaspace.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;
import com.bryce.metaspace.bean.MetaSpaceContent;
import com.bryce.metaspace.bean.MetaSpaceProperty;
import com.bryce.metaspace.bean.MetaSpaceType;
import com.bryce.metaspace.mapper.MetaSpaceMapper;

/**
 * 数据缓存处理器
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-24.
 */
@Component
public class MetaSpaceCacheHandle implements InitializingBean {

    @Resource
    MetaSpaceMapper metaspaceMapper;



    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static Map<String, MetaSpaceCache> mapCaches = new ConcurrentHashMap<>();

    /** 有效状态key值 */
    public static final String VALID_STATE = "state";

    @Override
    public void afterPropertiesSet() throws Exception {
        setTypes();
        LOG.info("MapCacheHandle init setTypes complete...");
    }


    public void refreshTypePropertyContent() {
        LOG.info("MapCacheHandle refresh start...");
        mapCaches.clear();
        LOG.info("MapCacheHandle refresh clear...");
        setTypes();
        LOG.info("MapCacheHandle refresh complete...");
    }

    /**
     * 设置所有字典类型
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-05-09.
     */
    private void setTypes() {
        List<MetaSpaceType> metaSpaceTypes = metaspaceMapper.selectList(null);
        for (int i = 0; i < metaSpaceTypes.size(); i++) {
            MetaSpaceType metaSpaceType = metaSpaceTypes.get(i);
            String typeCodeRecord = metaSpaceType.getTypeCode();
            MetaSpaceCache metaSpaceCache = mapCaches.get(typeCodeRecord);
            if (metaSpaceCache == null) {
                metaSpaceCache = new MetaSpaceCache();
            }
            metaSpaceCache.setType(conversionType(metaSpaceType));
            mapCaches.put(typeCodeRecord, metaSpaceCache);
        }
    }

    /**
     * 查询所有字典类型
     *
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-25.
     */
    public List<MetaSpaceTypeDTO> getTypes() {
        List<MetaSpaceTypeDTO> metaSpaceTypeDTOs = new ArrayList<>();
        Set<Entry<String, MetaSpaceCache>> set = mapCaches.entrySet();
        for (Entry<String, MetaSpaceCache> entry : set) {
            metaSpaceTypeDTOs.add(entry.getValue().getType());
        }
        return metaSpaceTypeDTOs;
    }

    /**
     * 获得字典类型
     *
     * @param typeCode
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-24.
     */
    public MetaSpaceTypeDTO getType(String typeCode) {
        MetaSpaceCache metaSpaceCache = mapCaches.get(typeCode);
        if (metaSpaceCache == null || metaSpaceCache.getType() == null) {
            if (!setType(typeCode)) {
                return null;
            }
        }
        return mapCaches.get(typeCode).getType();
    }

    /**
     * 查询数据库重设字典类型
     *
     * @param typeCode
     *
     *        Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-24.
     */
    public boolean setType(String typeCode) {
        MetaSpaceType metaSpaceType = metaspaceMapper.getType(typeCode);
        if (metaSpaceType == null) {
            return false;
        }
        MetaSpaceCache metaSpaceCache = mapCaches.get(typeCode);

        // 重设字典类型的时候线程安全
        if (metaSpaceCache == null) {
            metaSpaceCache = new MetaSpaceCache();
        }
        synchronized (metaSpaceCache) {
            metaSpaceCache.setType(conversionType(metaSpaceType));
            mapCaches.put(typeCode, metaSpaceCache);
        }
        return true;
    }



    /**
     * 获得字典属性
     *
     * @param typeCode
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-24.
     */
    public List<MetaSpacePropertyDTO> getPropertys(String typeCode) {
        MetaSpaceCache metaSpaceCache = mapCaches.get(typeCode);
        if (metaSpaceCache == null || metaSpaceCache.getPropertys() == null) {
            if (!setPropertys(typeCode)) {
                return null;
            }
        }
        return mapCaches.get(typeCode).getPropertys();
    }

    /**
     * 查询数据库重设字典属性
     *
     * @param typeCode
     *
     *        Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-24.
     */
    public boolean setPropertys(String typeCode) {
        List<MetaSpaceProperty> metaSpacePropertys = metaspaceMapper.getTypePropertys(typeCode);
        if (metaSpacePropertys == null || metaSpacePropertys.size() == 0) {
            return false;
        }
        MetaSpaceCache metaSpaceCache = mapCaches.get(typeCode);
        List<MetaSpacePropertyDTO> metaSpacePropertyDTOs = metaSpacePropertys.stream().map(metaSpaceProperty -> {
            return conversionProperty(metaSpaceProperty);
        }).collect(Collectors.toList());
        // 重设字典属性的时候线程安全
        if (metaSpaceCache == null) {
            metaSpaceCache = new MetaSpaceCache();
        }
        synchronized (metaSpaceCache) {
            metaSpaceCache.setPropertys(metaSpacePropertyDTOs);
            mapCaches.put(typeCode, metaSpaceCache);
        }
        return true;
    }

    /**
     * 获得字典内容
     *
     * @param typeCode
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-24.
     */
    public JSONArray getContents(String typeCode) {
        MetaSpaceCache metaSpaceCache = mapCaches.get(typeCode);
        if (metaSpaceCache == null || metaSpaceCache.getContents() == null) {
            if (!setContents(typeCode)) {
                return new JSONArray();
            }
        }
        return mapCaches.get(typeCode).getContents();
    }

    /**
     * 查询数据库重设字典内容
     *
     * @param typeCode
     *
     *        Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-24.
     */
    public boolean setContents(String typeCode) {
        List<MetaSpaceContent> metaSpaceContents = metaspaceMapper.getTypeAllContents(typeCode);
        if (metaSpaceContents == null || metaSpaceContents.size() == 0) {
            return false;
        }
        JSONArray contents = conversionContents(metaSpaceContents);
        MetaSpaceCache metaSpaceCache = mapCaches.get(typeCode);
        // 重设字典内容的时候线程安全
        if (metaSpaceCache == null) {
            metaSpaceCache = new MetaSpaceCache();
        }
        synchronized (metaSpaceCache) {
            metaSpaceCache.setContents(contents);
            mapCaches.put(typeCode, metaSpaceCache);
        }
        return true;
    }

    /**
     * 字典内容po类转JSONArray
     * 
     * 将key-value格式数据转为业务数据格式，返回业务数据集合
     * 
     * 数据内容以key-value显示存储 key(property_code)，value(property_content): provinceCode，430000 ;
     * provinceName，湖南省; cityCode，430100; cityName，长沙市; districtCode，430122; districtName，望城区;
     * 
     * 根据rowId确定数据属于字典同一项，返回业务格式的json数据
     * {"provinceCode":"430000","provinceName":"湖南省","cityCode":"430100","cityName":
     * "长沙市","districtCode":"430122","districtName":"望城区"}
     */
    private JSONArray conversionContents(List<MetaSpaceContent> metaSpaceContents) {
        JSONArray datas = new JSONArray();
        JSONObject json = new JSONObject();
        Long rowId = metaSpaceContents.get(0).getRowId();
        Integer size = metaSpaceContents.size();
        for (int i = 0; i < size; i++) {
            if (metaSpaceContents.get(i).getRowId().longValue() != rowId.longValue()) {
                if (i > 0) {
                    json.put(VALID_STATE, metaSpaceContents.get(i - 1).getState());
                }
                datas.add(json);
                rowId = metaSpaceContents.get(i).getRowId();
                json = new JSONObject();
                json.put(metaSpaceContents.get(i).getPropertyCode(), metaSpaceContents.get(i).getContent());
            }
            json.put(metaSpaceContents.get(i).getPropertyCode(), metaSpaceContents.get(i).getContent());
        }
        json.put(VALID_STATE, metaSpaceContents.get(size - 1).getState());
        datas.add(json);
        return datas;
    }

    /**
     * 字典属性po类转dto类
     */
    private MetaSpacePropertyDTO conversionProperty(MetaSpaceProperty metaSpaceProperty) {
        MetaSpacePropertyDTO metaSpacePropertyDTO = new MetaSpacePropertyDTO();
        metaSpacePropertyDTO.setTypeCode(metaSpaceProperty.getTypeCode());
        metaSpacePropertyDTO.setPropertyCode(metaSpaceProperty.getPropertyCode());
        metaSpacePropertyDTO.setPropertyName(metaSpaceProperty.getPropertyName());
        metaSpacePropertyDTO.setPropertyDesc(metaSpaceProperty.getPropertyDesc());
        return metaSpacePropertyDTO;
    }

    /**
     * 字典类型po类转dto类
     */
    private MetaSpaceTypeDTO conversionType(MetaSpaceType metaSpaceType) {
        MetaSpaceTypeDTO metaSpaceTypeDTO = new MetaSpaceTypeDTO();
        metaSpaceTypeDTO.setTypeCode(metaSpaceType.getTypeCode());
        metaSpaceTypeDTO.setTypeName(metaSpaceType.getTypeName());
        metaSpaceTypeDTO.setTypeDesc(metaSpaceType.getTypeDesc());
        return metaSpaceTypeDTO;
    }



}
