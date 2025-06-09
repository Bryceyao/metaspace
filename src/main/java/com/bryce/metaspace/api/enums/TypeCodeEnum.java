package com.bryce.metaspace.api.enums;

/**
 * 基础数据字典分类枚举.
 * 
 * 仅供参考，后续添加的字典分类可不在枚举列表中、不影响使用
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-06.
 */
public enum TypeCodeEnum {
    AREAS("areas","行政区划"),
    NATIONS("nations","中国各民族"),
    CERTIFICATEC("certificatec","证件类型"),
    POLITICS("politics","政治面貌"),
    RELATIONSHIPS("relationships","社会关系"),
    MARRIAGES("marriages","婚姻状况"),
    DEGREE("degree","学历"),
    CURRENCY("currency","国际币种"),
    COUNTRIES_REGIONS("countriesRegions","国家和地区 "),
    COLLEGE("college","中国高等院校"),
    INDUSTRY("industry","行业分类"),
    VOCATION("vocation","职业分类"),
    SEX("sex","性别"),
    EMPLOYMENT("employment","从业状况"),
    COMPANY_REGISTER_TYPE("companyRegisterType","企业登记注册类型"),
    LANGUAGES("languages","国际语言类型 ISO 639-1 Code"),
    PAY_CMB_AREAS("payCmbAreas","财务招行地区代码"),
    PAY_ACC_CONDITIONS("payAccConditions","财务对账条件"),
    PAY_CUSTOMER_TYPE("payCustomerType","财务客户、主体类型"),
    PAY_ORG_TYPE("payOrgType","财务机构类型"),
    PAY_METHODS("payMethods","财务还款方式"),
    PAY_RECEIVABLESMODEL("payReceivablesModel","财务收款模式"),
    PAY_LEDGERACCOUNT("payLedgerAccount","财务分账模式"),
    PAY_ACCSTATEMENT("payAccStatement","财务对账内容"),
    PAY_ACCCYCLE("payAccCycle","财务对账周期"),
    PAY_FEE_TYPE("payFeeType","财务费用类型"),
    PAY_CMB_CURRENCY("payCmbCurrency","财务招行货币代码"),
    LOAN_APPLICATION_STATE("loanApplicationState","进件系统 贷款申请状态"),
    LENDER_BANKCODE("lenderBankCode","财务系统，标准银行编码（中国工商银行 ICBC,中国农业银行 ABC）"),
    LENDER_UMP_BANKCODE("lenderUMPBankCode","财务系统，联动优势银行编码(UNION MOBILE PAY)"),
    LENDER_ALLINPAY_BANKCODE("lenderAllinpayBankCode","财务系统，通联银行编码"),
    LENDER_WECHAT_BANKCODE("lenderweChatBankCode","财务系统，微信银行编码"),
    LENDER_RELA_BANK_CODE("lenderRelaBankCode","财务系统，支付通道银行编码关系对照");

    private String code;
    
    private String desc;

    private TypeCodeEnum(String code,String desc) {
        this.code = code;
        this.desc=desc;
    }

    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
}
