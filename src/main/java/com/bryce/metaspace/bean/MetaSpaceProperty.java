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
 * 元数据属性
 * </p>
 *
 * @author Bryce
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MetaSpaceProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 全局序列号
     */
    @TableId(value = "property_id", type = IdType.AUTO)
    private Long propertyId;

    /**
     * 类型编码（areas）
     */
    private String typeCode;

    /**
     * 属性编码（province_code，province_name）
     */
    private String propertyCode;

    /**
     * 属性名称（省级行政区代码，省级行政区名称）
     */
    private String propertyName;

    /**
     * 属性说明（省级行政区名称：省、自治区、直辖市、特别行政区）
     */
    private String propertyDesc;

    /**
     * 有效状态，1 有效，0无效
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;


}
