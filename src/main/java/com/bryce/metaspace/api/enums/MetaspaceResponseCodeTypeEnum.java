package com.bryce.metaspace.api.enums;

import com.bryce.metaspace.api.common.CommonResponse;

/**
 * 基础数据服务响应码枚举
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-12.
 */
public enum MetaspaceResponseCodeTypeEnum {


    /**
     * 警告，已失效的属性内容,请更新到最新版本
     */
    WARN_STATE("10599999", "警告，已失效的属性内容,请更新到最新版本"),
    /**
     * 失败，参数typeCode无效
     */
    FAIL_TYPE_CODE("10510201", "失败，未能根据参数typeCode查询到字典分类"),
    /**
     * 失败，未配置字典分类的字段集合
     */
    FAIL_PROPERTY("10510202", "失败，未配置字典分类的字段集合"),
    /**
     * 失败，根据提交参数、未查询到字典内容
     */
    FAIL_CONTENT("10510203", "失败，根据提交参数、未查询到字典内容"),
    /**
     * 失败，参数typeCode已存在的
     */
    FAIL_EXIST_TYPE("10510204", "失败，参数typeCode已存在的"),
    /**
     * 失败，参数jsonArrayPropertys序列化为MetaSpacePropertyDTO对象失败
     */
    FAIL_JSON_PROPERTYS("10510205", "失败，参数jsonArrayPropertys序列化为MetaSpacePropertyDTO对象失败"),
    
    FAIL_BUSI("10510210", "失败，业务处理失败"),
    /**
     * 拒绝，参数typeCode不能为空
     */
    REJECT_TYPE_CODE("10510101", "拒绝，参数typeCode不能为空"),
    /**
     * 拒绝，参数propertyCode不能为空
     */
    REJECT_PROPERTY_CODE("10510102", "拒绝，参数propertyCode不能为空"),
    /**
     * 拒绝，参数propertyContent不能为空
     */
    REJECT_CONTENT("10510103", "拒绝，参数propertyContent不能为空"),
    /**
     * 拒绝，参数disPropertyCode不能为空
     */
    REJECT_DIS_PROPERTY("10510104", "拒绝，参数disPropertyCode不能为空"),
    /**
     * 拒绝，参数disPropertyCode不存在
     */
    REJECT_NOT_DIS_PROPERTY("10510105", "拒绝，错误的参数disPropertyCode"),
    /**
     * 拒绝，参数typeCodes不能为空
     */
    REJECT_TYPE_CODES("10510101", "拒绝，参数typeCodes不能为空");

 // 将当前枚举类的返回信息数据注入到统一返回的MAP对象里
    static {
        for (MetaspaceResponseCodeTypeEnum responsCodeTypeEnum : MetaspaceResponseCodeTypeEnum.values()) {
            CommonResponse.RESPONSE_MAP.put(responsCodeTypeEnum.getCode(), responsCodeTypeEnum.getMessage());
        }
    }
    
    private String code;

    private String message;

    private MetaspaceResponseCodeTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    
}
