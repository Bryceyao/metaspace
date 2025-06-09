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
 * 元数据内容
 * </p>
 *
 * @author Bryce
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MetaSpaceContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 全局序列号
     */
    @TableId(value = "content_id", type = IdType.AUTO)
    private Long contentId;

    /**
     * 类型编码（areas）
     */
    private String typeCode;

    /**
     * 属性编码（province_code，province_name）
     */
    private String propertyCode;

    /**
     * 属性内容（430000，湖南省）
     */
    private String content;

    /**
     * 有效状态，1 有效，0无效
     */
    private Integer state;

    /**
     * 行数据ID，用来确定多个元数据属于同一个枚举对象
     */
    private Long rowId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;


}
