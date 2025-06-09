package com.bryce.metaspace.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bryce.metaspace.SimpleTest;
import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.enums.TypeCodeEnum;
import com.bryce.metaspace.api.service.MetaspaceService;
import com.bryce.metaspace.api.util.BeanPropertyUtil;

public class BeanPropertyUtilTest extends SimpleTest {
    @Resource
    MetaspaceService metaspaceService;
    
    /**
     * 根据区县编号设置区县名称 Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-22.
     */
    @Test
    public void setNameByCode() {
        List<User> users = new ArrayList<User>();
        users.add(new User(1, "110102"));
        users.add(new User(2, "110105"));
        users.add(new User(3, "110106"));
        users.add(new User(4, "110107"));
        users.add(new User(5, "110105"));
        // 所有code内容以逗号分隔
        String disCodes = BeanPropertyUtil.getObjectCodes(users, "disCode");
        System.out.println("disCodes======" + disCodes);
        String typeCode = TypeCodeEnum.AREAS.getCode();
        // 请求基础数据服务，根据code查询字典枚举内容
        CommonResponse<JSONArray> metaResponse = metaspaceService.getTypeContents(typeCode, "districtCode", disCodes);
        if (CommonResponse.isSuccess(metaResponse)) {
            JSONArray metaDatas = metaResponse.getData();// 枚举内容
            System.out.println("metaDatas=====" + metaDatas.toJSONString());
            // 将字典枚举的属性替换到对象属性中
            BeanPropertyUtil.setNameByCode(users, "disCode", "disName", metaDatas, "districtCode", "districtName");
            System.out.println("users======" + JSONObject.toJSONString(users));
        }
    }

    /**
     * 根据区县编号设置 区县名称、省名称
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-22.
     */
    @Test
    public void setNamesByCode() {
        List<User> users = new ArrayList<User>();
        users.add(new User(1, "110102"));
        users.add(new User(2, "110105"));
        users.add(new User(3, "110106"));
        users.add(new User(4, "110107"));
        users.add(new User(5, "110105"));
        // 所有code内容以逗号分隔
        String disCodes = BeanPropertyUtil.getObjectCodes(users, "disCode");
        System.out.println("disCodes======" + disCodes);
        String typeCode = TypeCodeEnum.AREAS.getCode();
        // 请求基础数据服务，根据code查询字典枚举内容
        CommonResponse<JSONArray> metaResponse = metaspaceService.getTypeContents(typeCode, "districtCode", disCodes);
        if (CommonResponse.isSuccess(metaResponse)) {
            JSONArray metaDatas = metaResponse.getData();// 枚举内容
            System.out.println("nmetaDatas=====" + metaDatas.toJSONString());
            String[] namePropertys = {"disName", "proName"};
            String[] metaNames = {"districtName", "provinceName"};
            // 将字典枚举的属性替换到对象属性中
            BeanPropertyUtil.setNamesByCode(users, "disCode", namePropertys, metaDatas, "districtCode", metaNames);
            System.out.println("users======" + JSONObject.toJSONString(users));
        }
    }
}
