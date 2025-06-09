package com.bryce.metaspace.api.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;


/**
 * 元数据管理服务.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-06.
 */
public interface MetaManageService {
    /**
     * 新增字典类型.
     *
     * @param metaSpaceTypeDTO
     * @return
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-05.
     */
    CommonResponse<String> addType(@NotNull(message = "字典类型信息 不能为空") MetaSpaceTypeDTO metaSpaceTypeDTO);

    /**
     * 单个字典类型下，新增修改字典属性.
     *
     * @param typeCode
     * @param metaSpacePropertyDTOs
     * @return
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-08.
     */
    CommonResponse<String> addUpdatePropertys(String typeCode,List<MetaSpacePropertyDTO> metaSpacePropertyDTOs);

    /**
     * 新增修改字典内容.
     * 字典所有属性必须在JSONArray对象中存在，并且值不为空，无内容可默认填"0"
     *
     * @param typeCode 类型编码（参数举例：areas）
     * @param uProperty 字典唯一索引key，根据这个字段判断是新增还是修改（参数举例：districtCode）
     * @param jsonContents JSONArray格式的字典内容，key为字典属性。必须包涵uProperty，且属性唯一(参数举例：[{"cityCode":"440300","cityName":"深圳市",
     *        "districtCode":"440301","districtName":"市辖区","id":1931,"provinceCode":"440000",
     *        "provinceName":"广东省"},{"cityCode":"440300","cityName":"深圳市","districtCode":"440303",
     *        "districtName":"罗湖区","id":1932,"provinceCode":"440000","provinceName":"广东省"},{
     *        "cityCode":"440300","cityName":"深圳市","districtCode":"440304","districtName":"福田区","id"
     *        :1933,"provinceCode":"440000","provinceName":"广东省"},{"cityCode":"440300","cityName":
     *        "深圳市","districtCode":"440305","districtName":"南山区","id":1934,"provinceCode":"440000",
     *        "provinceName":"广东省"},{"cityCode":"440300","cityName":"深圳市","districtCode":"440306",
     *        "districtName":"宝安区","id":1935,"provinceCode":"440000","provinceName":"广东省"},{
     *        "cityCode":"440300","cityName":"深圳市","districtCode":"440307","districtName":"龙岗区","id"
     *        :1936,"provinceCode":"440000","provinceName":"广东省"},{"cityCode":"440300","cityName":
     *        "深圳市","districtCode":"440308","districtName":"盐田区","id":1937,"provinceCode":"440000",
     *        "provinceName":"广东省"}] )
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-05.
     */
    CommonResponse<String> addUpdateContents(String typeCode, String uProperty, String jsonArrayContents);
}
