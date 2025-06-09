package com.bryce.metaspace.infrastructure;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;

/**
 * 数据缓存对象
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on on 2017-12-06.
 */
public class MetaSpaceCache implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7105143100512146646L;

    private MetaSpaceTypeDTO type;
    
    private List<MetaSpacePropertyDTO> propertys;
    
    private JSONArray contents;
    
    public MetaSpaceTypeDTO getType() {
        return type;
    }

    public void setType(MetaSpaceTypeDTO type) {
        this.type = type;
    }

    public List<MetaSpacePropertyDTO> getPropertys() {
        return propertys;
    }

    public void setPropertys(List<MetaSpacePropertyDTO> propertys) {
        this.propertys = propertys;
    }

    public JSONArray getContents() {
        return contents;
    }

    public void setContents(JSONArray contents) {
        this.contents = contents;
    }

   
    
}
