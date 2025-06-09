package com.bryce.metaspace.api.dto;


import java.io.Serializable;
import lombok.Data;

/**
 * 元数据属性
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2020年7月9日.
 */
@Data
public class MetaSpacePropertyDTO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -3143802259986311297L;

    private String typeCode;// 类型编码（areas）
    private String propertyCode;// 属性编码（province_code，province_name）
    private String propertyName;// 属性名称（省级行政区代码，省级行政区名称）
    private String propertyDesc = "0";// 属性说明（省级行政区名称：省、自治区、直辖市、特别行政区）

    public MetaSpacePropertyDTO(){
        
    }
    
    public MetaSpacePropertyDTO(String typeCode,String propertyCode,String propertyName,String propertyDesc){
        this.typeCode=typeCode;
        this.propertyCode=propertyCode;
        this.propertyName=propertyName;
        this.propertyDesc=propertyDesc;
    }
}
