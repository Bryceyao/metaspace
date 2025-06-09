package com.bryce.metaspace.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 元数据类型
 * </p>
 *
 * @author Bryce
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MetaSpaceType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 全局序列号
     */
    @TableId(value = "type_id", type = IdType.AUTO)
    private Long typeId;

    /**
     * 类型编码（areas，certificatec,politics，relationships，marriages，degree）
     */
    private String typeCode;

    /**
     * 类型名称（行政区划，证件类型，政治面貌，社会关系，婚姻状况，学历）
     */
    private String typeName;

    /**
     * 类型说明（国标GBT2260-2013，国标 GAT517-2004，国标 GBT4762-1984，国标 GBT4761-1984+自定义内容，国标 GB4766-1984，国标 GBT4658-2006）
     */
    private String typeDesc;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;


}
