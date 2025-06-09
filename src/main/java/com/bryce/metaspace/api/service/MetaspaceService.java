package com.bryce.metaspace.api.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;

/**
 * 字典查询服务.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-06.
 */
public interface MetaspaceService {


    /**
     * 根据类型code获得字典类型集合
     *
     * @param typeCodes 可为null，查询所有类型。
     * @see com.bryce.metaspace.enums.TypeCodeEnum
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-01.
     */
    CommonResponse<List<MetaSpaceTypeDTO>> getTypes(String... typeCodes);


    /**
     * 查询字典属性.
     * 
     * data为 com.bryce.metaspace.dto.MetaSpacePropertyDTO集合
     *
     * @param typeCode @see com.bryce.metaspace.enums.TypeCodeEnum 类型编码
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-01.
     */
    CommonResponse<List<MetaSpacePropertyDTO>> getTypePropertys(String typeCode);

    /**
     * 查询字典所有枚举对象列表.
     * 
     * data为JSONArray
     *
     * @param typeCode @see com.bryce.metaspace.enums.TypeCodeEnum
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-01.
     */
    CommonResponse<JSONArray> getTypeAllContents(String typeCode);
    
    /**
     * 查询多个字典所有枚举对象列表
     *
     * @param typeCodes
     * @return
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-03-07.
     */
    CommonResponse<JSONObject> getTypesAllContents(String typeCodes);

    /**
     * 查询字典单个枚举对象.
     * 
     * 示例： 请求参数 typeCode=areas,propertyCode=cityCode,propertyContent=440300
     * 返回参数{"code":0,"result":{"districtCode":"440301","districtName":"市辖区","cityName":"深圳市",
     * "cityCode":"440300","provinceCode":"440000","provinceName":"广东省"},"desc":"请求成功","timestamp":
     * 1512530051078}
     *
     * @param typeCode @see com.bryce.metaspace.enums.TypeCodeEnum 类型编码 
     * @param propertyCode 属性编码
     * @param propertyContent 属性内容
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-01.
     */
    CommonResponse<JSONObject> getTypeContent(String typeCode, String propertyCode, String propertyContent);

    /**
     * 查询字典某类枚举对象.
     * 
     * 示例： 请求参数 typeCode=areas,propertyCode=cityCode,propertyContent=440300
     * 返回参数{"code":0,"result":[{"districtCode":"440301","districtName":"市辖区","cityName":"深圳市",
     * "cityCode": "440300","provinceCode":"440000","provinceName":"广东省"},{"districtCode":"440303",
     * "districtName":"罗湖区","cityName":"深圳市","cityCode":"440300","provinceCode":"440000",
     * "provinceName":"广东省"},{"districtCode":"440304","districtName":"福田区","cityName":"深圳市",
     * "cityCode":"440300","provinceCode":"440000","provinceName":"广东省"},{"districtCode":"440305",
     * "districtName":"南山区","cityName":"深圳市","cityCode":"440300","provinceCode":"440000",
     * "provinceName":"广东省"},{"districtCode":"440306","districtName":"宝安区","cityName":"深圳市",
     * "cityCode":"440300","provinceCode":"440000","provinceName":"广东省"},{"districtCode":"440307",
     * "districtName":"龙岗区","cityName":"深圳市","cityCode":"440300","provinceCode":"440000",
     * "provinceName":"广东省"},{"districtCode":"440308","districtName":"盐田区","cityName":"深圳市",
     * "cityCode":"440300","provinceCode":"440000","provinceName":"广东省"}],"desc":"请求成功","timestamp":
     * 1512529704265}
     *
     * 
     * @param typeCode @see com.bryce.metaspace.enums.TypeCodeEnum 类型编码
     * @param propertyCode 属性编码
     * @param propertyContents 属性内容，多个以英文逗号分隔
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-01.
     */
    CommonResponse<JSONArray> getTypeContents(String typeCode, String propertyCode, String propertyContent);


    /**
     * 查询字典去重枚举对象
     * 
     * 示例： 请求参数
     * typeCode=areas,disPropertyCode=cityCode,propertyCode=provinceCode,propertyContent=540000
     * 返回参数{"code": 0,"message": "请求成功","result": [{"districtCode": "540101","districtName":
     * "市辖区","cityName": "拉萨市","cityCode": "540100","provinceCode": "540000","provinceName":
     * "西藏自治区"}, {"districtCode": "540202","districtName": "桑珠孜区","cityName": "日喀则市","cityCode":
     * "540200","provinceCode": "540000","provinceName": "西藏自治区"}, {"districtCode":
     * "540302","districtName": "卡若区","cityName": "昌都市","cityCode": "540300","provinceCode":
     * "540000","provinceName": "西藏自治区"}, {"districtCode": "540402","districtName":
     * "巴宜区","cityName": "林芝市","cityCode": "540400","provinceCode": "540000","provinceName":
     * "西藏自治区"}, {"districtCode": "540501","districtName": "市辖区","cityName": "山南市","cityCode":
     * "540500","provinceCode": "540000","provinceName": "西藏自治区"}, {"districtCode":
     * "542421","districtName": "那曲县","cityName": "那曲地区","cityCode": "542400","provinceCode":
     * "540000","provinceName": "西藏自治区"}, {"districtCode": "542521","districtName":
     * "普兰县","cityName": "阿里地区","cityCode": "542500","provinceCode": "540000","provinceName":
     * "西藏自治区"}]}
     * 
     *
     * @param typeCode @see com.bryce.metaspace.enums.TypeCodeEnum 类型编码
     * @param disPropertyCode 去重依据
     * @param propertyCode 属性编码，可为空查询所有数据
     * @param propertyContent 属性内容，多个以英文逗号分隔，可为空查询所有数据
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-23.
     */
    CommonResponse<JSONArray> getTypeDistinctContents(String typeCode, String disPropertyCode, String propertyCode,
            String propertyContent);

    /**
     * 查询所有省市区县级联关系 
     * 
     * 示例： 请求参数cKey=code，nKey=name,childKey=child
     * 返回参数[ { "code": "110000", "name": "北京市", "child": [ { "code": "110100", "name": "北京市",
     * "child": [ { "code": "110101", "name": "东城区" }, { "code": "110102", "name": "西城区" }, {
     * "code": "……", "name": "……" } ] } ] }, { "code": "430000", "name": "湖南省", "child": [ { "code":
     * "430100", "name": "长沙市", "child": [ { "code": "430101", "name": "市辖区" }, { "code": "430102",
     * "name": "芙蓉区" }, { "code": "……", "name": "……" } ] }, { "code": "430200", "name": "株洲市",
     * "child": [ { "code": "……", "name": "……" } ] }, { "code": "……", "name": "……", "child": [ {} ]
     * } ] }, { "code": "440000", "name": "广东省", "child": [ {} ] }, { "code": "……", "name": "……" } ]
     *
     * @param cKey 地区编码指定key值
     * @param nKey 地区名称指定key值
     * @param childKey 下级地区指定key值
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-26.
     */
    CommonResponse<JSONArray> getAreasPCD(String cKey, String nKey, String childKey);
}
