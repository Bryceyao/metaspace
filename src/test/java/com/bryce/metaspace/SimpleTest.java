package com.bryce.metaspace;


import javax.annotation.Resource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.bryce.metaspace.mapper.MetaSpaceMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SimpleTest extends AbstractJUnit4SpringContextTests{

    @Resource
    protected MetaSpaceMapper metaSpaceMapper;
}
