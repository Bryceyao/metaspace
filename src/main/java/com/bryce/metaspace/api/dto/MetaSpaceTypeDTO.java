package com.bryce.metaspace.api.dto;

import java.io.Serializable;
import lombok.Data;
import javax.validation.constraints.NotNull;


/**
 * 元数据类型
 */
@Data
public class MetaSpaceTypeDTO implements Serializable {



    /**
     * 
     */
    private static final long serialVersionUID = 3088284871782633847L;
    @NotNull(message = "typeCode 类型编码 不能为空")
    private String typeCode;// 类型编码（areas，certificatec,politics，relationships，marriages，degree）
    @NotNull(message = "typeName 类型名称 不能为空")
    private String typeName;// 类型名称（行政区划，证件类型，政治面貌，社会关系，婚姻状况，学历）
    private String typeDesc = "0";// 类型说明（国标GBT2260-2013，国标 GAT517-2004，国标 GBT4762-1984，国标
                                  // GBT4761-1984+自定义内容，国标 GB4766-1984，国标 GBT4658-2006）

    public MetaSpaceTypeDTO() {

    }
    
    public MetaSpaceTypeDTO(String typeCode,String typeName,String typeDesc) {
        this.typeCode=typeCode;
        this.typeName=typeName;
        this.typeDesc=typeDesc;
    }

 
}
