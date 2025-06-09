package com.bryce.metaspace.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bryce.metaspace.bean.MetaSpaceContent;
import com.bryce.metaspace.bean.MetaSpaceProperty;
import com.bryce.metaspace.bean.MetaSpaceType;

@Repository
public interface MetaSpaceMapper extends BaseMapper<MetaSpaceType> {

    /**
     * 查询字典分类.
     *
     * @param typeCode
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-04.
     */
    public MetaSpaceType getType(String typeCode) ;

    /**
     * 查询某个字典分类的字段集合.
     *
     * @param typeCode
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-04.
     */
    public List<MetaSpaceProperty> getTypePropertys(String typeCode) ;
    
    
    /**
     * 查询某个字典分类的所有数据集合.
     *
     * @param typeCode
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-04.
     */
    public List<MetaSpaceContent> getTypeContents(String typeCode);
    
    /**
     * 查询某个字典分类的所有数据集合.
     *
     * @param typeCode
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-04.
     */
    public List<MetaSpaceContent> getTypeAllContents(String typeCode) ;

    /**
     * 查询某个字典分类、某个字段类型、某个字段内容记录
     *
     * @param typeCode
     * @param propertyCode
     * @param propertyContent
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-04.
     */
    public List<MetaSpaceContent> getTypeContents(String typeCode, String propertyCode, String propertyContent,
            boolean state) ;
    
}
