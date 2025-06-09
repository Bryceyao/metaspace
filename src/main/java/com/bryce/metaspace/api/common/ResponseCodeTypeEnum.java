package com.bryce.metaspace.api.common;


/**
 * 响应码枚举类.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-11-02.
 */
public enum ResponseCodeTypeEnum {
    /**
     * 成功
     */
    SUCCESS("0", "处理成功"),
    /**
     * 失败
     */
    FAIL("9", "未知错误"),
    /**
     * 拒绝 缺少orgId参数
     */
    REJECT_ORG_ID("11011", "拒绝，缺少orgId参数"),
    /**
    /**
     * 拒绝 缺少busiCode参数
     */
    REJECT_BUSI_CODE("10711001", "拒绝，缺少busiCode参数"),
    /**
     * 拒绝 缺少dataTypeCode参数
     */
    REJECT_DATA_TYPE_CODE("10711002", "拒绝，缺少dataTypeCode参数"),
    /**
         * 拒绝 缺少dataValue参数
     */
    REJECT_DATA_VALUE("10711003", "拒绝，缺少dataValue参数"),
    /**
     * 失败，根据参数orgId，busiCode，dataTypeCode找不到状态有效的黑名单类型
     */
    FAIL_TYPE_INEFFECTIVE("10712003", "失败，根据参数找不到状态有效的黑名单类型");
   
    private String code;

    private String desc;
    
    /**
     * 初始化时将当前业务的返回码和返回码对应的错误信息添加到统一返回对象中
     */
    static {
        for (ResponseCodeTypeEnum responsCodeTypeEnum : ResponseCodeTypeEnum.values()) {
            if (CommonResponse.RESPONSE_MAP.containsKey(responsCodeTypeEnum.getCode())) {
                throw new RuntimeException("业务返回码重复，请确认！重复的业务码是：" + responsCodeTypeEnum.getCode());
            }
            CommonResponse.RESPONSE_MAP.put(responsCodeTypeEnum.getCode(), responsCodeTypeEnum.getDesc());
        }
    }

    private ResponseCodeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResponseCodeTypeEnum getByValue(String code) {
        for (ResponseCodeTypeEnum codeType : values()) {
            if (codeType.getCode() == code) {
                return codeType;
            }
        }
        return FAIL;
    }


    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
