package com.bryce.metaspace.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bryce.metaspace.SimpleTest;
import com.bryce.metaspace.api.common.CommonResponse;
import com.bryce.metaspace.api.common.ResponseCodeTypeEnum;
import com.bryce.metaspace.api.dto.MetaSpacePropertyDTO;
import com.bryce.metaspace.api.dto.MetaSpaceTypeDTO;
import com.bryce.metaspace.api.exception.MetaspaceException;
import com.bryce.metaspace.api.service.MetaManageService;
import com.bryce.metaspace.api.service.MetaspaceService;

/**
 * 本地初始化数据服务. 可用作将网络搜集到的数据导入本地数据库
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-03-30.
 */
public class ManageInitDataV2Test extends SimpleTest {
    @Resource
    MetaManageService manageService;
    @Resource
    MetaspaceService metaspaceService;

    /**
     * 一次性新增修改多个枚举类 第一个属性为唯一主键
     * 
     * @param dataFile
     *
     *        Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-03-30.
     */
    @Test
    public void addTypeValueName() {
        Map<String, MetaSpaceTypeDTO> types = new HashMap<>();
        Map<String, List<MetaSpacePropertyDTO>> propertys = new HashMap<>();
        Map<String, JSONArray> contents = new HashMap<>();
        String pathname = ManageServiceTest.class.getResource("").getPath() + "data/addDataLender6.sql" ;
        System.out.println(pathname);
        try {
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));

            BufferedReader br = new BufferedReader(reader);
            String lineString = null;
            boolean flag = true;
            while ((lineString = br.readLine()) != null) {
                lineString=lineString.trim();
                if(StringUtils.isEmpty(lineString)){
                    continue;
                }
                if ("contentFlag".equals(lineString)) {
                    flag = false;
                    continue;
                }
                String[] lineStrings = lineString.split(",|\\s+");
                // 类型或者属性
                if (flag) {
                    // 一行三个,并且第二个非单词，数据类型
                    if (lineStrings.length == 3 && !isdanci(lineStrings[1])) {
                        MetaSpaceTypeDTO metaSpaceTypeDTO =
                                new MetaSpaceTypeDTO(lineStrings[0], lineStrings[1], lineStrings[2]);
                        types.put(lineStrings[0], metaSpaceTypeDTO);
                    }
                    // 一行四个,并且第二个为单词，三四个为汉字 数据属性
                    else if (lineStrings.length == 4 && isdanci(lineStrings[1]) && !isdanci(lineStrings[2])
                            && !isdanci(lineStrings[3])) {
                        MetaSpacePropertyDTO metaSpacePropertyDTO = new MetaSpacePropertyDTO(lineStrings[0],
                                lineStrings[1], lineStrings[2], lineStrings[3]);
                        List<MetaSpacePropertyDTO> property = propertys.get(lineStrings[0]);
                        if (property == null) {
                            property = new ArrayList<>();
                        }
                        property.add(metaSpacePropertyDTO);
                        propertys.put(lineStrings[0], property);
                    } else {
                        throw new MetaspaceException("不能识别的类型或者属性===" + lineString);
                    }
                }
                // 数据内容
                else {
                    MetaSpaceTypeDTO type = types.get(lineStrings[0]);
                    if (type == null) {
                        throw new MetaspaceException("没有配置类型===" + lineStrings[0]);
                    }
                    List<MetaSpacePropertyDTO> property = propertys.get(lineStrings[0]);
                    if (property == null) {
                        throw new MetaspaceException("没有配置属性===" + lineStrings[0]);
                    }
                    if (property.size() != lineStrings.length - 1) {
                        throw new MetaspaceException(
                                "属性与内容长度不匹配==property=" + JSON.toJSONString(property) + ",content=" + lineStrings);
                    }
                    JSONObject jsonContent = new JSONObject();
                    for (int i = 0; i < property.size(); i++) {
                        jsonContent.put(property.get(i).getPropertyCode(), lineStrings[i + 1]);
                    }
                    JSONArray content = contents.get(lineStrings[0]);
                    if (content == null) {
                        content = new JSONArray();
                    }
                    content.add(jsonContent);
                    contents.put(lineStrings[0], content);
                }
            }
            br.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n");
        Set<String> keys = types.keySet();
        for (String key : keys) {
            CommonResponse<List<MetaSpaceTypeDTO>> getRsp = metaspaceService.getTypes(key);
            if (CommonResponse.isSuccess(getRsp) && getRsp.getData().size() == 0) {
                System.out.println(key + "=====addtype======" + JSONObject.toJSONString(types.get(key)));
                CommonResponse<String> addRsp = manageService.addType(types.get(key));
                assertEquals(addRsp.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
            }
            List<MetaSpacePropertyDTO> property=propertys.get(key);
            System.out.println(key + "=====addproperty======" + JSONObject.toJSONString(property));
            CommonResponse<String> addPropertys = manageService.addUpdatePropertys(key, property);
            assertEquals(addPropertys.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
            System.out.println(key + "=====addContent======" + contents.get(key).size());
            CommonResponse<String> addContent = manageService.addUpdateContents(key,
                    property.get(0).getPropertyCode(), JSON.toJSONString(contents.get(key)));
            assertEquals(addContent.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
        }
        System.out.println("\n\n");
    }

    public static void main(String[] args) {
        String str = "fundparty_type  value   资金方类型编码 资金方类型编码";
        String[] lineStrings = str.split(",|，|\\s+");
        System.err.println(lineStrings.length);
    }

    // 单词字符(所有的字母、所有数字和下划线)
    private static boolean isdanci(String str) {
        Pattern pattern = Pattern.compile("^\\w+$");
        Matcher isSixNum = pattern.matcher(str);
        return isSixNum.matches();
    }
}
