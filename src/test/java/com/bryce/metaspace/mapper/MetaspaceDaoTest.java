package com.bryce.metaspace.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.bryce.metaspace.SimpleTest;
import com.bryce.metaspace.api.enums.TypeCodeEnum;
import com.bryce.metaspace.bean.MetaSpaceProperty;

/**
 * 基础dao查询修改.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-12.
 */
public class MetaspaceDaoTest extends SimpleTest {

    @Resource
    MetaSpacePropertyMapper metaSpacePropertyMapper;
    
    /**
     * 测试查询修改.
     *
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-08.
     */
    @Test
    public void daoFindAndUpdate() {
        List<MetaSpaceProperty> metaSpacePropertys =
                        metaSpaceMapper.getTypePropertys(TypeCodeEnum.SEX.getCode());
        assertNotNull(metaSpacePropertys.get(0));
        System.out.println("\n");
        for (int i = 0; i < metaSpacePropertys.size(); i++) {
            System.out.println(JSONObject.toJSONString(metaSpacePropertys.get(i)));
        }
        System.out.println("\n");
        
        int i = metaSpacePropertyMapper.updateById(metaSpacePropertys.get(0));
        assertSame(i,1);
    }
    
}
