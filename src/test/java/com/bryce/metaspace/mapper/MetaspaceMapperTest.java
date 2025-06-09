package com.bryce.metaspace.mapper;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.bryce.metaspace.SimpleTest;
import com.bryce.metaspace.bean.MetaSpaceType;


public class MetaspaceMapperTest extends SimpleTest {
    
    @Resource
    MetaSpaceMapper metaSpaceMapper;
    
    @Test
    public void metaSpaceMapper() {
        MetaSpaceType result = metaSpaceMapper.getType("lenderCmbTradeType");
        System.out.println("========="+JSON.toJSONString(result));
    }
    
}
