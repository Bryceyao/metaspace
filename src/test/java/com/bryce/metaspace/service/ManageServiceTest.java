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
import com.bryce.metaspace.api.service.MetaManageService;
import com.bryce.metaspace.api.service.MetaspaceService;

/**
 * 管理数据字典测试用例.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-12.
 */
public class ManageServiceTest extends SimpleTest {
    @Resource
    MetaManageService manageService;
    @Resource
    MetaspaceService metaspaceService;

    /**
     * 新增字典类型.新增成功
     * 由于字典类型不提供删除方法，此用例新增的数据需要手动删除
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-12.
     */
    @Test
    public void addTypeSuccess() {
        MetaSpaceTypeDTO metaSpaceTypeDTO = new MetaSpaceTypeDTO();
        String typeCode = "test" + System.currentTimeMillis();
        String jsonStr =
                "{\"typeCode\":\"" + typeCode + "\",\"typeName\":\"自动化测试、测试新增字典类型\",\"typeDesc\":\"自动化测试、测试新增字典类型\"}";
        jsonStr ="{\"typeCode\":\"languages\",\"typeName\":\"国际语言类型\",\"typeDesc\":\"国际语言  ISO 639-1 Code，参考 https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes\"}";
        metaSpaceTypeDTO = JSONObject.parseObject(jsonStr, MetaSpaceTypeDTO.class);
        CommonResponse<String> response = manageService.addType(metaSpaceTypeDTO);
        assertEquals(response.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }
    
    /**
     * 新增字典类型.类型已存在
     *
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-12.
     */
    @Test
    public void addTypeFail(){
        MetaSpaceTypeDTO metaSpaceTypeDTO = new MetaSpaceTypeDTO();
        String typeCode = TypeCodeEnum.SEX.getCode();
        String jsonStr =
                "{\"typeCode\":\"" + typeCode + "\",\"typeName\":\"性别\",\"typeDesc\":\"国标 GBT 2261-1-2003\"}";
        metaSpaceTypeDTO = JSONObject.parseObject(jsonStr, MetaSpaceTypeDTO.class);
        CommonResponse<String> response = manageService.addType(metaSpaceTypeDTO);
        assertEquals(response.getCode(), MetaspaceResponseCodeTypeEnum.FAIL_EXIST_TYPE.getCode());
    }

    /**
     * 批量修改字典属性.
     *
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void addUpdateProperty() {
        String typeCode= TypeCodeEnum.AREAS.getCode();
        CommonResponse<List<MetaSpacePropertyDTO>> getPropertysResponse= metaspaceService.getTypePropertys(typeCode);
        assertEquals(getPropertysResponse.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
        List<MetaSpacePropertyDTO> metaSpacePropertyDTOs =  getPropertysResponse.getData();
        System.out.println("\n");
        System.out.println(JSONArray.toJSONString(metaSpacePropertyDTOs));
        System.out.println("\n");
        
        CommonResponse<String> responseSuccess = manageService.addUpdatePropertys(typeCode, metaSpacePropertyDTOs);
        assertEquals(responseSuccess.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
        
        CommonResponse<String> responseNotExit = manageService.addUpdatePropertys("notExit", metaSpacePropertyDTOs);
        assertEquals(responseNotExit.getCode(), MetaspaceResponseCodeTypeEnum.FAIL_TYPE_CODE.getCode());
    }


    /**
     * 新增或修改字典枚举对象.
     *
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void addUpdateContents() {
        String typeCode =TypeCodeEnum.MARRIAGES.getCode();
        CommonResponse<JSONArray> getContentsResponse= metaspaceService.getTypeAllContents(typeCode);
        assertEquals(getContentsResponse.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
        
        System.out.println("\n");
        System.out.println(JSONObject.toJSONString(getContentsResponse));
        System.out.println("\n");
        JSONArray jsonContents=(JSONArray) getContentsResponse.getData();
        
        String uProperty = "marryCode";
        String jsonArrayContents =jsonContents.toString();
        CommonResponse<String> response = manageService.addUpdateContents(typeCode, uProperty, jsonArrayContents);
        assertEquals(response.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

    @Test
    public void addContents() {
        String typeCode="payAccConditions";
        String uProperty = "accConditionsCode";
        String jsonArrayContents ="[{\"accConditionsCode\":\"1\",\"accConditionsName\":\"贷款号\"},"+
                "{\"accConditionsCode\":\"2\",\"accConditionsName\":\"流水号\"},"+
                "{\"accConditionsCode\":\"3\",\"accConditionsName\":\"日期\"},"+
                "{\"accConditionsCode\":\"4\",\"accConditionsName\":\"银行账号\"},"+
                "{\"accConditionsCode\":\"5\",\"accConditionsName\":\"金额\"},"+
                "{\"accConditionsCode\":\"6\",\"accConditionsName\":\"交易类型\"},"+
                "{\"accConditionsCode\":\"7\",\"accConditionsName\":\"费用类型\"},"+
                "{\"accConditionsCode\":\"8\",\"accConditionsName\":\"手机号\"},"+
                "{\"accConditionsCode\":\"9\",\"accConditionsName\":\"姓名\"}]";
        CommonResponse<String> response = manageService.addUpdateContents(typeCode, uProperty, jsonArrayContents);
        assertEquals(response.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }

}
