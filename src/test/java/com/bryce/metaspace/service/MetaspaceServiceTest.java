package com.bryce.metaspace.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bryce.metaspace.SimpleTest;
import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.common.ResponseCodeTypeEnum;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;
import com.bryce.metaspace.api.enums.MetaspaceResponseCodeTypeEnum;
import com.bryce.metaspace.api.enums.TypeCodeEnum;
import com.bryce.metaspace.api.service.MetaspaceService;

/**
 * 使用数据字典测试用例.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-12.
 */
public class MetaspaceServiceTest extends SimpleTest {
    @Resource
    MetaspaceService metaspaceService;

    /**
     * 查询多个字典枚举内容
     *
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-03-30.
     */
    @Test
    public void getTypesAllContents(){
        CommonResponse<JSONObject> rsp=metaspaceService.getTypesAllContents("lender_manualtransfer_type,lenderOrgType,lenderFeeType,lenderCustomerType,lenderMethods,lender_payment_method,lender_trade_type,lender_audit_status,lender_trade_use,lenderAccConditions,lender_tiecard_channel,lenderWeChatBankCode,lenderCmbAreas,lender_warning_type,lender_account_prop,lender_loan_state,lenderUMPBankCode,lender_line_flag,lender_workflow_trade_type,lenderCmbCurrency,lender_account_use,lenderAllinpayBankCode,lenderAccCycle,lender_loan_term,lenderReceivablesModel,lenderAccount,lenderRelaBankCode,lender_payment_channel,lender_fundparty_type,lenderAccStatement,loanApplicationState");
        System.err.println( JSONObject.toJSONString(rsp));
    }
    
    /**
     * 查询所有字典类型
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getTypes() {
        CommonResponse<List<MetaSpaceTypeDTO>> res = metaspaceService.getTypes("");
        System.out.println("\n\n");
        System.out.println("=====getTypes======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
        assertEquals(res.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    /**
     * 根据字典code查询字典类型
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getTypeByCodes() {
        String[] typeCodes = {TypeCodeEnum.COMPANY_REGISTER_TYPE.getCode(), TypeCodeEnum.NATIONS.getCode()};
        CommonResponse<List<MetaSpaceTypeDTO>> res = metaspaceService.getTypes(typeCodes);
        System.out.println("\n\n");
        System.out.println("=====getTypeByCodes======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
        assertEquals(res.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    /**
     * 查询单个字典下的属性key值
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getTypePropertys() {
        CommonResponse<List<MetaSpacePropertyDTO>> res=metaspaceService.getTypePropertys(TypeCodeEnum.PAY_FEE_TYPE.getCode());
        System.out.println("\n\n");
        System.out.println("=====getTypePropertys======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
        assertEquals(res.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    /**
     * 查询单个字典下的属性key值 异常情况
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getTypePropertysExec() {
        String typeCode = "notExist";
        CommonResponse<List<MetaSpacePropertyDTO>> res = metaspaceService.getTypePropertys(typeCode);
        System.out.println("\n\n");
        System.out.println("=====getTypePropertysExec======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
        assertEquals(res.getCode(), MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());

        String typeCoden = null;
        CommonResponse<List<MetaSpacePropertyDTO>> resn = metaspaceService.getTypePropertys(typeCoden);
        System.out.println("\n\n");
        System.out.println("=====getTypePropertysExec======" + JSONObject.toJSONString(resn));
        System.out.println("\n\n");
        assertEquals(res.getCode(), MetaspaceResponseCodeTypeEnum.REJECT_TYPE_CODE.getCode());
    }


    /**
     * 查询单个字典的字典类型，属性key值，枚举内容
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getTypeInfo() {
        String typeCode = "payFeeType";

        CommonResponse<List<MetaSpaceTypeDTO>> resType = metaspaceService.getTypes(typeCode);

        CommonResponse<List<MetaSpacePropertyDTO>> resProperty = metaspaceService.getTypePropertys(typeCode);

        CommonResponse<JSONArray> resContent = metaspaceService.getTypeAllContents(typeCode);

        System.out.println("resType=======" + JSONObject.toJSONString(resType.getData()));
        System.out.println("");
        System.out.println("resProperty=======" + JSONObject.toJSONString(resProperty.getData()));
        System.out.println("");
        System.out.println("resContent=======" + JSONObject.toJSONString(resContent.getData()));
        assertEquals(resType.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
        assertEquals(resProperty.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
        assertEquals(resContent.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    /**
     * 查询单个字典的字典类型下的某个枚举内容
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getTypeContent() {
        String typeCode = TypeCodeEnum.AREAS.getCode();
        String propertyCode = "districtCode";
        String propertyContent = "440305";
        CommonResponse<JSONObject> res = metaspaceService.getTypeContent(typeCode, propertyCode, propertyContent);
        System.out.println("\n\n");
        System.out.println("=====getTypePropertys======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
        assertEquals(res.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    /**
     * 查询单个字典的字典类型下的某类枚举内容
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getTypeContents() {
        String typeCode = TypeCodeEnum.AREAS.getCode();
        String propertyCode = "cityName";
        String propertyContent = "深圳市";
        CommonResponse<JSONArray> res = metaspaceService.getTypeContents(typeCode, propertyCode, propertyContent);
        System.out.println("\n\n");
        System.out.println("=====getTypePropertys======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
        assertEquals(res.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    @Test
    public void getTypeDistinctContents() {
        String typeCode = TypeCodeEnum.AREAS.getCode();
        String disPropertyCode = "provinceCode";
        // String propertyCode = "provinceCode";
        // String propertyContent = "440000";
        CommonResponse<JSONArray> res = metaspaceService.getTypeDistinctContents(typeCode, disPropertyCode, null, null);
        System.out.println("\n\n");
        System.out.println("=====getTypePropertys======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
        assertEquals(res.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }


    /**
     * 统计各个字典的枚举个数.
     *
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getTypeAllContentsCount() {
        System.out.println("\n");
        for (TypeCodeEnum typeCodeEnum: TypeCodeEnum.values()){
            CommonResponse<List<MetaSpaceTypeDTO>> resType = metaspaceService.getTypes(typeCodeEnum.getCode());

            CommonResponse<JSONArray> resContent = metaspaceService.getTypeAllContents(typeCodeEnum.getCode());
            JSONArray datas = (JSONArray) resContent.getData();
            System.out.println(datas.size() + "======" + JSONObject.toJSONString(resType.getData()));
        }
        System.out.println("\n\n");
    }

    /**
     * 获取省市区级联数据
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void getAreasPCD() {
        CommonResponse<JSONArray> res = metaspaceService.getAreasPCD("code", "name", "child");
        System.out.println("\n\n");
        System.out.println("=====getTypeByCodes======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
        assertEquals(res.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

}
