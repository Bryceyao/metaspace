package com.bryce.metaspace.constants;

public interface Urls {
    interface V1 {
        String ROOT = "/metaspace/v1";
        /**
         * 基础数据服务
         */
        interface Render {
            String GET_TYPES = "/render/getTypes"; //根据类型code获得字典类型集合.
            String GET_TYPE_PROPERTYS = "/render/getTypePropertys"; //查询字典属性.
            String GET_TYPE_ALL_CONTENTS = "/render/getTypeAllContents"; //查询字典所有枚举对象列表
            String GET_TYPES_ALL_CONTENTS = "/render/getTypesAllContents"; //查询字典所有枚举对象列表
            String GET_TYPE_CONTENT = "/render/getTypeContent"; //查询字典单个枚举对象
            String GET_TYPE_CONTENTS = "/render/getTypeContents"; //查询字典某类枚举对象
            String GET_TYPE_DISTINCT_CONTENTS = "/render/getTypeDistinctContents"; //查询字典某类枚举对象
            String GET_AREAS_PCD = "/render/getAreasPCD"; //查询所有省市区县级联关系 
        }
        
        /**
         * 管理基础数据
         */
        interface Manage {
            String ADD_TYPE = "/manage/addType"; //新增字典类型.
            String ADD_UPDATE_PROPERTYS = "/manage/addUpdatePropertys"; //单个字典类型下，新增修改字典属性.
            String ADD_UPDATE_CONTENTS = "/manage/addUpdateContents"; //新增修改字典内容.
        }
    }
}
