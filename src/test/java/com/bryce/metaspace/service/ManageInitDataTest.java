package com.bryce.metaspace.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import com.bryce.metaspace.api.enums.TypeCodeEnum;
import com.bryce.metaspace.api.service.MetaManageService;
import com.bryce.metaspace.api.service.MetaspaceService;

/**
 * 本地初始化数据服务.
 * 可用作将网络搜集到的数据导入本地数据库
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-20.
 */
public class ManageInitDataTest extends SimpleTest {
    @Resource
    MetaManageService manageService;
    @Resource
    MetaspaceService metaspaceService;
    
    @Test
    public void relaBankCode(){
        JSONArray resContentLender = metaspaceService.getTypeAllContents(TypeCodeEnum.LENDER_BANKCODE.getCode()).getData();
        JSONArray resContentTongpay = metaspaceService.getTypeAllContents(TypeCodeEnum.LENDER_ALLINPAY_BANKCODE.getCode()).getData();
        JSONArray resContentUMP = metaspaceService.getTypeAllContents(TypeCodeEnum.LENDER_UMP_BANKCODE.getCode()).getData();
        JSONArray resContentWechat = metaspaceService.getTypeAllContents(TypeCodeEnum.LENDER_WECHAT_BANKCODE.getCode()).getData();
        JSONArray rela=new JSONArray();
        for (int i = 0; i < resContentLender.size(); i++) {
            JSONObject json = resContentLender.getJSONObject(i);
            json.put("tCode", "-");
            json.put("uCode", "-");
            json.put("wCodeDebit", "-");
            json.put("wCodeCredit", "-");
            String bName=json.getString("bankName");
            for (int j = 0; j < resContentTongpay.size(); j++) {
                if(bName.indexOf(resContentTongpay.getJSONObject(j).getString("bankName"))>=0){
                    json.put("tCode", resContentTongpay.getJSONObject(j).getString("bankCode"));
                    break;
                }
            }
            for (int j = 0; j < resContentUMP.size(); j++) {
                if(bName.indexOf(resContentUMP.getJSONObject(j).getString("bankName"))>=0){
                    json.put("uCode", resContentUMP.getJSONObject(j).getString("bankCode"));
                    break;
                }
            }
            for (int j = 0; j < resContentWechat.size(); j++) {
                String[] bNames=resContentWechat.getJSONObject(j).getString("bankName").split("\\(");
                if(bName.indexOf(bNames[0])>=0){
                    if("借记卡)".equals(bNames[1])){
                        json.put("wCodeDebit", resContentWechat.getJSONObject(j).getString("bankCode"));
                    }
                    else{
                        json.put("wCodeCredit", resContentWechat.getJSONObject(j).getString("bankCode"));
                    }
                    if(!"-".equals(json.getString("wCodeDebit"))&&!"-".equals(json.getString("wCodeCredit"))){
                        break;
                    }
                }
            }
            json.remove("state");
            rela.add(json);
            System.out.println(json.toString());
        }
    }
    
    /**
     * 新增字典类型
     */
    @Test
    public void addType() {
        MetaSpaceTypeDTO metaSpaceTypeDTO = new MetaSpaceTypeDTO();
        String jsonStr ="{\"typeCode\":\"languages\",\"typeName\":\"国际语言类型\",\"typeDesc\":\"国际语言  ISO 639-1 Code，参考 https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes\"}";
        metaSpaceTypeDTO = JSONObject.parseObject(jsonStr, MetaSpaceTypeDTO.class);
        CommonResponse response = manageService.addType(metaSpaceTypeDTO);
        assertEquals(response.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
    }
    
    /**
     * 新增字典属性
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-20.
     */
    @Test
    public void addUpdateProperty() {
        String typeCode= "lenderRelaBankCode";
        List<MetaSpacePropertyDTO> metaSpacePropertyDTOs=new ArrayList<>();
        metaSpacePropertyDTOs.add(new MetaSpacePropertyDTO(typeCode, "bankCode", "标准银行编码", "标准银行编码"));
        metaSpacePropertyDTOs.add(new MetaSpacePropertyDTO(typeCode, "bankName", "标准银行名称", "标准银行名称"));
        metaSpacePropertyDTOs.add(new MetaSpacePropertyDTO(typeCode, "UMPBankCode", "联动优势银行编码", "联动优势银行编码"));
        metaSpacePropertyDTOs.add(new MetaSpacePropertyDTO(typeCode, "allinpayBankCode", "通联银行编码", "通联银行编码"));
        metaSpacePropertyDTOs.add(new MetaSpacePropertyDTO(typeCode, "weChatCreditCode", "微信银行信用卡编码", "微信银行信用卡编码"));
        metaSpacePropertyDTOs.add(new MetaSpacePropertyDTO(typeCode, "weChatDebitCode", "微信银行借记卡编码", "微信银行借记卡编码"));
        System.out.println("\n");
        System.out.println(JSONArray.toJSONString(metaSpacePropertyDTOs));
        System.out.println("\n");
        
        CommonResponse responseSuccess = manageService.addUpdatePropertys(typeCode, metaSpacePropertyDTOs);
        assertEquals(responseSuccess.getCode(), ResponseCodeTypeEnum.SUCCESS.getCode());
        
    }

    /**
     * 从txt文件读取数据，保存到字典枚举里
     *
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-06.
     */
    @Test
    public void addAreasContents() {
        // initDataFromTxt(TypeCodeEnum.AREAS.getValue(), "districtCode", "areas.sql");
        // initDataFromTxt(TypeCodeEnum.CERTIFICATEC.getValue(), "certCode", "certificatec.sql");
        // initDataFromTxt(TypeCodeEnum.DEGREE.getValue(), "degreeCode", "degree.sql");
        // initDataFromTxt(TypeCodeEnum.EMPLOYMENT.getValue(), "code", "employment.sql");
        // initDataFromTxt(TypeCodeEnum.MARRIAGES.getValue(), "marryCode", "marriages.sql");
        // initDataFromTxt(TypeCodeEnum.POLITICS.getValue(), "politicsCode", "politics.sql");
        // initDataFromTxt(TypeCodeEnum.SEX.getValue(), "code", "sex.sql");
        // initDataFromTxt(TypeCodeEnum.RELATIONSHIPS.getValue(), "relaCode", "relationships.sql");
        initDataFromTxt("lenderRelaBankCode", "bankCode", "lenderRelaBankCode.sql");
//        initDataFromTxt("loanApplicationState", "bankCode", "loanApplicationState.sql");
        // initDataFromTxt(TypeCodeEnum.CURRENCY.getValue(), "curCode", "currency.sql");
        // initDataFromTxt(TypeCodeEnum.NATIONS.getValue(), "nationNum", "nations.sql");
        // initDataFromTxt(TypeCodeEnum.COUNTRIES_REGIONS.getValue(), "numericCode",
        // "countriesRegions.sql");
        // initDataFromTxt(TypeCodeEnum.COMPANY_REGISTER_TYPE.getValue(), "registerCode",
        // "companyRegisterType.sql");
    }

    private void initDataFromTxt(String typeCode, String uProperty, String dataFile) {
        String jsonArrayContents = "";

        String pathname = ManageServiceTest.class.getResource("").getPath() + "data/" + dataFile;
        System.out.println(pathname);
        try {
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));

            BufferedReader br = new BufferedReader(reader);
            StringBuffer buf = new StringBuffer();
            String tempString = null;
            while ((tempString = br.readLine()) != null) {
                buf.append(tempString);
            }
            br.close();
            reader.close();
            jsonArrayContents = buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CommonResponse res = manageService.addUpdateContents(typeCode, uProperty, jsonArrayContents);
        System.out.println("\n\n");
        System.out.println(typeCode + "=====initDataFromTxt======" + JSONObject.toJSONString(res));
        System.out.println("\n\n");
    }
    
    
    /**
     * 将学校地址为城市名称转为城市编码，并打印输出.
     * 
     * 数据导入的时候，从教育部全国高校名单复制出来的城市名称全部是中文， 在这里查询所有高校数据和所有行政区划数据，将高校的城市名称全程替换成城市编码，并补全省编码，打印输出。
     * 将打印结果复制到excel中格式化，得到标准数据导入格式，再将标准的json数据通过ManageServiceTest.addAreasContents方法导入到数据库
     * 
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void changeCollegeCityNameToCode() {
        CommonResponse res1 = metaspaceService.getTypeAllContents(TypeCodeEnum.COLLEGE.getCode());
        JSONArray colleges = (JSONArray) res1.getData();

        CommonResponse res2 = metaspaceService.getTypeAllContents(TypeCodeEnum.AREAS.getCode());
        JSONArray areas = (JSONArray) res2.getData();

        for (int i = 0; i < colleges.size(); i++) {
            JSONObject college = colleges.getJSONObject(i);
            String cityCode = "";
            String provinceCode = "";
            String cityName = college.getString("cityCode");
            for (int j = 0; j < areas.size(); j++) {
                if (cityName.equals(areas.getJSONObject(j).getString("cityName"))) {
                    cityCode = areas.getJSONObject(j).getString("cityCode");
                    provinceCode = areas.getJSONObject(j).getString("provinceCode");
                    break;
                }
                if (cityName.equals(areas.getJSONObject(j).getString("cityCode"))) {
                    cityCode = areas.getJSONObject(j).getString("cityCode");
                    provinceCode = areas.getJSONObject(j).getString("provinceCode");
                    break;
                }
                if (cityName.equals(areas.getJSONObject(j).getString("districtName"))) {
                    cityCode = areas.getJSONObject(j).getString("cityCode");
                    provinceCode = areas.getJSONObject(j).getString("provinceCode");
                    break;
                }
            }
            if ("".equals(provinceCode) || "".equals(cityCode)) {
                System.out.println("无法匹配城市======" + college.toJSONString());
            } else {
                college.put("cityCode", cityCode);
                college.put("provinceCode", provinceCode);
            }
        }

        System.out.println("\n\n");
        for (int i = 1500; i < colleges.size(); i++) {
            JSONObject college = colleges.getJSONObject(i);
            System.out.println(college.getString("code") + " " + college.getString("name") + " "
                    + college.getString("provinceCode") + " " + college.getString("cityCode"));
        }
        System.out.println("\n\n");
    }

    /**
     * 匹配院校登记，打印输出结果.
     *
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-07.
     */
    @Test
    public void changeCollegeCategory() {
        CommonResponse res1 = metaspaceService.getTypeAllContents(TypeCodeEnum.COLLEGE.getCode());
        JSONArray colleges = (JSONArray) res1.getData();
        String t211 =
                "北京大学,清华大学,北京工业大学,北京理工大学,北京化工大学,中国农业大学,北京协和医学院,北京师范大学,中国传媒大学,对外经济贸易大学,中央音乐学院,中国政法大学,南开大学,天津医科大学,河北工业大学,内蒙古大学,大连理工大学,东北大学,吉林大学,东北师范大学,哈尔滨工程大学,东北林业大学,同济大学,华东理工大学,华东师范大学,上海财经大学,南京大学,东南大学,南京理工大学,河海大学,南京农业大学,南京师范大学,安徽大学,合肥工业大学,福州大学,山东大学,中国石油大学(华东),武汉大学,中国地质大学(武汉),华中农业大学,中南财经政法大学,中南大学,中山大学,华南理工大学,海南大学,四川大学,西南交通大学,四川农业大学,西南财经大学,云南大学,中国人民大学,北京交通大学,北京航空航天大学,北京科技大学,北京邮电大学,北京林业大学,北京中医药大学,北京外国语大学,中央财经大学,北京体育大学,中央民族大学,华北电力大学(北京),天津大学,华北电力大学(保定),太原理工大学,辽宁大学,大连理工大学盘锦校区,大连海事大学,延边大学,哈尔滨工业大学,东北农业大学,复旦大学,上海交通大学,东华大学,上海外国语大学,上海大学,苏州大学,南京航空航天大学,中国矿业大学,江南大学,中国药科大学,浙江大学,中国科学技术大学,厦门大学,南昌大学,中国海洋大学,郑州大学,华中科技大学,武汉理工大学,华中师范大学,湖南大学,湖南师范大学,暨南大学,华南师范大学,广西大学,重庆大学,电子科技大学,西南大学,贵州大学,西藏大学,西北大学,西北工业大学,长安大学,陕西师范大学,青海大学,新疆大学,中国矿业大学(北京),中国地质大学(北京),北京邮电大学(宏福校区),哈尔滨工业大学(威海),山东大学威海分校,中国人民解放军第二军医大学,西安交通大学,西安电子科技大学,西北农林科技大学,兰州大学,宁夏大学,石河子大学,中国石油大学(北京),北京大学医学部,东北大学秦皇岛分校,上海交通大学医学院,中国人民解放军国防科学技术大学,第四军医大学";
        String t985 =
                "北京大学,清华大学,北京理工大学,北京协和医学院,中央民族大学,天津大学,大连理工大学盘锦校区,吉林大学,复旦大学,上海交通大学,南京大学,浙江大学,厦门大学,中国海洋大学,华中科技大学,中南大学,华南理工大学,重庆大学,西安交通大学,西北农林科技大学,北京大学医学部,哈尔滨工业大学(威海),山东大学威海分校,中国人民大学,北京航空航天大学,中国农业大学,北京师范大学,南开大学,大连理工大学,东北大学,哈尔滨工业大学,同济大学,华东师范大学,东南大学,中国科学技术大学,山东大学,武汉大学,湖南大学,中山大学,四川大学,电子科技大学,西北工业大学,兰州大学,东北大学秦皇岛分校,上海交通大学医学院,中国人民解放军国防科学技术大学";
        String yiben =
                "北方工业大学,北京工商大学,北京建筑大学,北京协和医学院,首都医科大学,首都师范大学,北京第二外国语学院,北京语言大学,北京物资学院,首都经济贸易大学,中华女子学院,北京联合大学,天津科技大学,天津工业大学,中国民航大学,天津理工大学,天津中医药大学,天津师范大学,天津职业技术师范大学,天津外国语大学,天津商业大学,天津财经大学,天津城建大学,河北大学,河北工程大学,石家庄经济学院,河北联合大学,河北科技大学,河北农业大学,河北医科大学,河北师范大学,石家庄铁道大学,燕山大学,河北科技师范学院,河北经贸大学,山西大学,太原科技大学,中北大学,山西农业大学,山西医科大学,山西师范大学,山西财经大学,内蒙古科技大学,内蒙古工业大学,内蒙古农业大学,内蒙古医科大学,内蒙古师范大学,内蒙古民族大学,赤峰学院,内蒙古财经大学,沈阳工业大学,沈阳航空航天大学,沈阳理工大学,辽宁科技大学,辽宁工程技术大学,辽宁石油化工大学,沈阳化工大学,大连交通大学,大连工业大学,沈阳建筑大学,沈阳农业大学,大连海洋大学,中国医科大学,辽宁医学院,大连医科大学,辽宁中医药大学,沈阳药科大学,辽宁师范大学,沈阳师范大学,大连外国语大学,东北财经大学,大连大学,沈阳工程学院,大连民族学院,长春理工大学,东北电力大学,长春工业大学,吉林农业大学,北华大学,吉林师范大学,长春师范大学,吉林财经大学,黑龙江大学,哈尔滨理工大学,东北石油大学,佳木斯大学,哈尔滨医科大学,黑龙江中医药大学,牡丹江医学院,哈尔滨师范大学,齐齐哈尔大学,哈尔滨商业大学,齐齐哈尔医学院,上海理工大学,上海海事大学,上海电力学院,上海海洋大学,上海中医药大学,上海师范大学,上海对外经贸大学,华东政法大学,上海工程技术大学,上海立信会计学院,上海金融学院,江苏科技大学,南京工业大学,常州大学,南京邮电大学,南京林业大学,江苏大学,南京信息工程大学,南通大学,南京医科大学,南京财经大学,苏州科技学院,扬州大学,南京审计学院,杭州电子科技大学,浙江工业大学,浙江理工大学,浙江海洋学院,温州医科大学,浙江师范大学,浙江工商大学,中国计量学院,宁波大学,安徽工业大学,安徽理工大学,安徽农业大学,安徽医科大学,安徽中医药大学,安徽师范大学,安徽财经大学,华侨大学,福建农林大学,集美大学,福建医科大学,福建中医药大学,福建师范大学,闽江学院,华东交通大学,南昌航空大学,江西理工大学,景德镇陶瓷学院,江西农业大学,江西师范大学,井冈山大学,江西财经大学,山东科技大学,青岛科技大学,青岛理工大学,山东农业大学,山东中医药大学,山东师范大学,曲阜师范大学,青岛大学,烟台大学,华北水利水电大学,河南理工大学,河南工业大学,河南科技大学,河南农业大学,河南大学,河南师范大学,河南财经政法大学,武汉科技大学,长江大学,武汉工程大学,湖北中医药大学,湖北大学,中南民族大学,三峡大学,湘潭大学,湖南科技大学,长沙理工大学,湖南农业大学,中南林业科技大学,湖南中医药大学,南华大学,湖南工业大学,汕头大学,华南农业大学,广州医科大学,深圳大学,广州大学,广东工业大学,广东外语外贸大学,南方医科大学,桂林电子科技大学,桂林理工大学,广西医科大学,广西师范大学,广西民族大学,海南师范大学,海南医学院,重庆邮电大学,重庆交通大学,重庆医科大学,西南政法大学,重庆理工大学,重庆工商大学,西南石油大学,成都理工大学,成都信息工程学院,西华大学,中国民用航空飞行学院,泸州医学院,成都中医药大学,川北医学院,西南民族大学,贵州大学,云南大学,昆明理工大学,云南农业大学,西南林业大学,昆明医科大学,大理学院,云南中医学院,云南师范大学,云南财经大学,云南民族大学,长安大学,西安电子科技大学,西北大学,西安理工大学,西安工业大学,西安建筑科技大学,西安科技大学,西安石油大学,陕西科技大学,西安工程大学,陕西师范大学,延安大学,西安外国语大学,西北政法大学,西安邮电大学,兰州理工大学,兰州交通大学,甘肃农业大学,西北师范大学,兰州财经大学,新疆农业大学,新疆医科大学,新疆师范大学,伊犁师范学院,新疆财经大学,贵州大学经济学院,华北理工大学,北京明园大学,渤海大学文理学院,中国民航飞行学院,南京水利科学研究院,贵州大学理学院";
        String erben =
                "北京服装学院,北京印刷学院,北京石油化工学院,北京电子科技学院,北京农学院,首都体育学院,外交学院,中国人民公安大学,国际关系学院,中国音乐学院,中央美术学院,中央戏剧学院,中国戏曲学院,北京电影学院,北京舞蹈学院,北京信息科技大学,北京城市学院,中国青年政治学院,首钢工学院,中国劳动关系学院,北京吉利学院,北京警察学院,中国科学院大学,天津农学院,天津体育学院,天津音乐学院,天津美术学院,天津天狮学院,河北建筑工程学院,河北北方学院,承德医学院,保定学院,河北民族师范学院,唐山师范学院,廊坊师范学院,衡水学院,石家庄学院,邯郸学院,邢台学院,沧州师范学院,唐山学院,华北科技学院,中国人民武装警察部队学院,河北体育学院,河北金融学院,北华航天工业学院,防灾科技学院,中央司法警官学院,河北传媒学院,河北美术学院,河北科技学院,河北外国语学院,河北中医学院,张家口学院,长治医学院,太原师范学院,山西大同大学,晋中学院,长治学院,运城学院,忻州师范学院,山西中医学院,吕梁学院,太原学院,山西应用科技学院,山西工商学院,太原工业学院,山西传媒学院,山西工程技术学院,呼伦贝尔学院,集宁师范学院,河套学院,呼和浩特民族学院,辽宁工业大学,沈阳医学院,渤海大学,鞍山师范学院,中国刑事警察学院,沈阳体育学院,沈阳音乐学院,鲁迅美术学院,辽宁对外经贸学院,沈阳大学,辽宁科技学院,辽宁警察学院,辽东学院,辽宁理工学院,辽宁传媒学院,营口理工学院,吉林建筑大学,吉林化工学院,长春中医药大学,通化师范学院,吉林工程技术师范学院,白城师范学院,吉林体育学院,吉林艺术学院,吉林华桥外国语学院,吉林工商学院,长春工程学院,吉林农业科技学院,吉林警察学院,长春大学,长春财经学院,吉林医药学院,黑龙江科技大学,黑龙江八一农垦大学,牡丹江师范学院,哈尔滨学院,大庆师范学院,绥化学院,哈尔滨体育学院,哈尔滨金融学院,黑龙江工业学院,黑龙江东方学院,哈尔滨信息工程学院,黑龙江工程学院,齐齐哈尔工程学院,黑河学院,上海应用技术学院,上海海关学院,上海体育学院,上海音乐学院,上海戏剧学院,上海电机学院,上海杉达学院,上海政法学院,上海第二工业大学,上海商学院,上海建桥学院,上海兴伟学院,上海科技大学,上海纽约大学,盐城工学院,徐州医学院,南京中医药大学,江苏师范大学,淮阴师范学院,盐城师范学院,江苏警官学院,南京体育学院,南京艺术学院,常熟理工学院,淮阴工学院,常州工学院,三江学院,南京工程学院,南京晓庄学院,江苏理工学院,淮海工学院,徐州工程学院,南通理工学院,南京森林警察学院,泰州学院,金陵科技学院,宿迁学院,江苏第二师范学院,西交利物浦大学,昆山杜克大学,浙江农林大学,浙江中医药大学,杭州师范大学,湖州师范学院,绍兴文理学院,台州学院,温州大学,丽水学院,嘉兴学院,中国美术学院,公安海警学院,浙江万里学院,浙江科技学院,宁波工程学院,浙江水利水电学院,浙江财经大学,浙江警察学院,衢州学院,浙江传媒学院,浙江树人学院,浙江越秀外国语学院,宁波大红鹰学院,浙江外国语学院,宁波诺丁汉大学,温州肯恩大学,安徽工程大学,蚌埠医学院,皖南医学院,阜阳师范学院,安庆师范学院,淮北师范大学,黄山学院,皖西学院,滁州学院,宿州学院,巢湖学院,淮南师范学院,铜陵学院,安徽建筑大学,安徽科技学院,安徽三联学院,合肥学院,蚌埠学院,池州学院,安徽新华学院,安徽文达信息工程学院,安徽外国语学院,合肥师范学院,福建工程学院,武夷学院,泉州师范学院,闽南师范大学,厦门理工学院,三明学院,龙岩学院,福建警察学院,莆田学院,仰恩大学,闽南理工学院,福州外语外贸学院,福建江夏学院,泉州信息工程学院,东华理工大学,江西中医药大学,赣南医学院,上饶师范学院,宜春学院,赣南师范学院,江西科技学院,景德镇学院,萍乡学院,江西科技师范大学,南昌工程学院,江西警察学院,新余学院,九江学院,江西工程学院,南昌理工学院,江西应用科技学院,江西服装学院,南昌工学院,南昌师范学院,济南大学,山东建筑大学,齐鲁工业大学,山东理工大学,青岛农业大学,潍坊医学院,泰山医学院,滨州医学院,济宁医学院,聊城大学,德州学院,滨州学院,鲁东大学,临沂大学,泰山学院,济宁学院,菏泽学院,山东财经大学,山东体育学院,山东艺术学院,山东万杰医学院,青岛滨海学院,枣庄学院,山东工艺美术学院,潍坊学院,山东警察学院,山东交通学院,山东工商学院,山东女子学院,烟台南山学院,潍坊科技学院,山东英才学院,青岛恒星科技学院,青岛黄海学院,山东协和学院,山东华宇工学院,齐鲁理工学院,山东政法学院,齐鲁师范学院,山东青年政治学院,山东管理学院,山东农业工程学院,郑州轻工业学院,中原工学院,河南科技学院,河南牧业经济学院,河南中医学院,新乡医学院,信阳师范学院,周口师范学院,安阳师范学院,许昌学院,南阳师范学院,洛阳师范学院,商丘师范学院,郑州航空工业管理学院,黄淮学院,平顶山学院,洛阳理工学院,新乡学院,信阳农林学院,安阳工学院,河南工程学院,南阳理工学院,河南城建学院,河南警察学院,黄河科技学院,铁道警察学院,郑州科技学院,郑州工业应用技术学院,郑州师范学院,郑州财经学院,黄河交通学院,商丘工学院,郑州升达经贸管理学院,武汉纺织大学,武汉轻工大学,湖北工业大学,湖北师范学院,黄冈师范学院,湖北民族学院,湖北文理学院,武汉体育学院,湖北美术学院,湖北汽车工业学院,湖北工程学院,湖北理工学院,湖北科技学院,湖北医药学院,江汉大学,湖北警官学院,荆楚理工学院,武汉音乐学院,湖北经济学院,武汉商学院,武汉生物工程学院,武汉工商学院,文华学院,武汉工程科技学院,湖北第二师范学院,吉首大学,湖南理工学院,湘南学院,衡阳师范学院,邵阳学院,怀化学院,湖南文理学院,湖南科技学院,湖南人文科技学院,湖南商学院,长沙医学院,长沙学院,湖南工程学院,湖南城市学院,湖南工学院,湖南财政经济学院,湖南警察学院,湖南女子学院,湖南第一师范学院,湖南医药学院,湖南涉外经济学院,长沙师范学院,湖南应用技术学院,湖南信息学院,湖南交通工程学院,广东海洋大学,广东医学院,广东药学院,韶关学院,惠州学院,韩山师范学院,岭南师范学院,肇庆学院,嘉应学院,广州体育学院,广州美术学院,星海音乐学院,广东技术师范学院,广东财经大学,广东白云学院,广州航海学院,广东警官学院,仲恺农业工程学院,五邑大学,广东金融学院,广东石油化工学院,东莞理工学院,佛山科学技术学院,广东培正学院,广东东软学院,广州商学院,广州工商学院,广东科技学院,广东理工学院,广东第二师范学院,南方科技大学,北京师范大学-香港浸会大学联合国际学院,香港中文大学（深圳）,广西科技大学,右江民族医学院,广西中医药大学,桂林医学院,广西师范学院,广西民族师范学院,河池学院,玉林师范学院,广西艺术学院,百色学院,梧州学院,广西财经学院,南宁学院,钦州学院,贺州学院,北海艺术设计学院,广西外国语学院,琼州学院,海口经济学院,重庆师范大学,重庆文理学院,重庆三峡学院,长江师范学院,四川外国语大学,四川美术学院,重庆科技学院,重庆工程学院,重庆警察学院,重庆第二师范学院,四川理工学院,西昌学院,四川师范大学,西华师范大学,绵阳师范学院,内江师范学院,宜宾学院,四川文理学院,乐山师范学院,成都体育学院,四川音乐学院,成都学院,成都工业学院,攀枝花学院,四川旅游学院,四川民族学院,四川警察学院,成都东软学院,成都文理学院,成都医学院,四川工业科技学院,四川文化艺术学院,成都师范学院,四川电影电视学院,贵阳医学院,遵义医学院,贵阳中医学院,贵州师范大学,遵义师范学院,铜仁学院,兴义民族师范学院,安顺学院,贵州工程应用技术学院,凯里学院,黔南民族师范学院,贵州财经大学,贵州民族大学,贵阳学院,六盘水师范学院,贵州师范学院,贵州理工学院,昭通学院,曲靖师范学院,普洱学院,保山学院,红河学院,云南艺术学院,玉溪师范学院,楚雄师范学院,云南警官学院,昆明学院,文山学院,云南经济管理学院,云南工商学院,西藏民族学院,西藏藏医学院,陕西中医学院,陕西理工学院,宝鸡文理学院,咸阳师范学院,渭南师范学院,西安体育学院,西安音乐学院,西安美术学院,西安文理学院,榆林学院,商洛学院,安康学院,西安培华学院,西安财经学院,西安医学院,西安欧亚学院,西安外事学院,西安翻译学院,西京学院,西安思源学院,陕西国际商贸学院,陕西服装工程学院,西安交通工程学院,陕西学前师范学院,西北民族大学,甘肃中医学院,兰州城市学院,陇东学院,天水师范学院,河西学院,甘肃政法学院,甘肃民族师范学院,兰州文理学院,兰州工业学院,青海师范大学,青海民族大学,北方民族大学,宁夏医科大学,宁夏师范学院,宁夏理工学院,银川能源学院,塔里木大学,喀什师范学院,新疆艺术学院,新疆工程学院,昌吉学院,新疆警察学院,河南教育学院,安徽大学江淮学院,北京电影学院现代创意媒体学院,北京师范大学－香港浸会大学联合国际学院,广东商学院华商学院,广西工学院鹿山学院,广西民族学院相思湖学院,广西中医学院赛恩斯新医药学院,贵州大学科技学院,贵州民族学院人文科技学院,国防科学技术大学,河海大学文天学院,衡阳师范学院南岳学院,华侨大学厦门工学院,江苏工业学院怀德学院,江苏科技大学苏州理工学院,昆明医学院海源学院,山东经济学院燕山学院,四川外语学院重庆南方翻译学院,天津外国语学院滨海外事学院,武汉科技大学城市学院,西南交通大学希望学院,浙江财经学院东方学院,中国地质大学（北京）,中国地质大学（武汉）,中国地质大学长城学院,中国矿业大学（北京）,中国石油大学（北京）,中南财经政法大学武汉学院,山东财经大学燕山学院,兰州市外语职业学院,集美大学财经学院,广西民族大学相思湖学院,广东财经大学华商学院,广州工商职业技术学院,浙江财经大学东方学院,浙江农林大学暨阳学院,北京民族大学,齐鲁医药学院,四川外国语大学国际教育学院,广西科技师范学院,泉州信息工程学院,黑龙江工商学院,西南大学育才学院,中国地质大学江城学院,鸡西大学,鸡西大学,厦门华厦学院,南京特殊教育师范学院,南京解放军信息工程学院,郑州大学西亚斯国际学院,贵州商学院,江西教育学院,北京演艺专修学院,西北农林科技大学动物科技学院";
        String[] t211s = t211.split(",");
        String[] t985s = t985.split(",");
        String[] yibens = yiben.split(",");
        String[] erbens = erben.split(",");
        for (int i = 0; i < colleges.size(); i++) {
            JSONObject college = colleges.getJSONObject(i);
            String name = college.getString("name");
            String category = "";
            for (int j = 0; j < t211s.length; j++) {
                if (name.equals(t211s[j])) {
                    category = "985/211";
                    break;
                }
            }
            if ("".equals(category)) {
                for (int j = 0; j < t985s.length; j++) {
                    if (name.equals(t985s[j])) {
                        category = "985/211";
                        break;
                    }
                }
            }
            if ("".equals(category)) {
                for (int j = 0; j < yibens.length; j++) {
                    if (name.equals(yibens[j])) {
                        category = "一本";
                        break;
                    }
                }
            }
            if ("".equals(category)) {
                for (int j = 0; j < erbens.length; j++) {
                    if (name.equals(erbens[j])) {
                        category = "二本";
                        break;
                    }
                }
            }
            if (!"".equals(category)) {
                college.put("category", category);
            }
        }

        System.out.println("\n\n");
        for (int i = 0; i < colleges.size(); i++) {
            JSONObject college = colleges.getJSONObject(i);
            System.out.println(college.getString("name") + " " + college.getString("category"));
        }
        System.out.println("\n\n");
    }
}
