package com.bryce.metaspace.api.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bryce.metaspace.api.exception.MetaspaceException;

/**
 * 对象属性设置工具
 * 场景：对象中有个属性保存字典code，返回前端需要显示一个或多个name，该工具提供统一解决方法
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-22.
 */
public class BeanPropertyUtil {

    /**
     * 获取集合对象的所有code内容
     *
     * @param lists
     * @param codeProperty
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-22.
     */
    public static String getObjectCodes(List<?> lists, String codeProperty) throws MetaspaceException {
        if (CollectionUtils.isEmpty(lists)) {
            throw new MetaspaceException("lists can not be EMPTY");
        }
        if (StringUtils.isEmpty(codeProperty)) {
            throw new MetaspaceException("codeProperty can not be EMPTY");
        }
        Object objOne = lists.get(0);
        if (Objects.isNull(objOne)) {
            throw new MetaspaceException("lists index 0 record can not be NULL");
        }
        Class<? extends Object> clazz = objOne.getClass();
        Method getReadCode = getReadCode(clazz, codeProperty);

        Set<String> mlinkedset = new LinkedHashSet<String>();
        int i = 0;
        try {
            for (; i < lists.size(); i++) {
                Object obj = lists.get(i);
                String codeValue = (String) getReadCode.invoke(obj);
                mlinkedset.add(codeValue);
            }
        } catch (Exception e) {
            throw new MetaspaceException(
                    "lists index [" + i + "] record can not getReadCode,message is [" + e.getMessage() + "]");
        }

        StringBuffer bf = new StringBuffer();
        for (String str : mlinkedset) {
            bf.append(str).append(",");
        }
        String codeValues = bf.substring(0, bf.length() - 1);
        return codeValues;
    }

    /**
     * 根据code值，设置集合对象的name值
     *
     * @param lists 对象集合
     * @param codeProperty 对象code属性名
     * @param nameProperty 对象name属性名
     * @param metaDate 字典数据
     * @param metaCode 字典code属性名
     * @param metaName 字典name属性名
     * @return
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-22.
     */
    public static List<?> setNameByCode(List<?> lists, String codeProperty, String nameProperty, JSONArray metaDates,
            String metaCode, String metaName) throws MetaspaceException {
        if (CollectionUtils.isEmpty(lists)) {
            throw new MetaspaceException("lists can not be EMPTY");
        }
        if (CollectionUtils.isEmpty(metaDates)) {
            throw new MetaspaceException("metaDate can not be EMPTY");
        }
        if (StringUtils.isEmpty(codeProperty) || StringUtils.isEmpty(nameProperty) || StringUtils.isEmpty(metaCode)
                || StringUtils.isEmpty(metaName)) {
            throw new MetaspaceException("Sting param can not be EMPTY");
        }
        Object objOne = lists.get(0);
        if (Objects.isNull(objOne)) {
            throw new MetaspaceException("lists index 0 record can not be NULL");
        }
        Class<? extends Object> clazz = objOne.getClass();
        Method getReadCode = getReadCode(clazz, codeProperty);
        Method setWriteName = setWriteName(clazz, nameProperty);

        int i = 0;
        try {
            for (; i < lists.size(); i++) {
                Object obj = lists.get(i);
                String codeValue = (String) getReadCode.invoke(obj);
                for (int j = 0; j < metaDates.size(); j++) {
                    if (codeValue.equals(metaDates.getJSONObject(j).getString(metaCode))) {
                        setWriteName.invoke(obj, metaDates.getJSONObject(j).getString(metaName));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new MetaspaceException("lists index [" + i + "] record codeProperty [" + codeProperty
                    + "] can not getRead or nameProperty [" + nameProperty + "] can not setWrite,message is ["
                    + e.getMessage() + "]");
        }

        return lists;
    }

    /**
     * 根据code值，设置集合对象的多个name值
     *
     * @param lists 对象集合
     * @param codeProperty 对象code属性名
     * @param namePropertys 对象name属性名
     * @param metaDates 字典数据
     * @param metaCode 字典code属性名
     * @param metaNames 字典name属性名
     * @return
     * @throws MetaspaceException
     *
     *         Created by Bryce Yao<sysyaoyulong@gmail.com> on 2018-01-22.
     */
    public static List<?> setNamesByCode(List<?> lists, String codeProperty, String[] namePropertys, JSONArray metaDates,
            String metaCode, String[] metaNames) throws MetaspaceException {
        if (CollectionUtils.isEmpty(lists)) {
            throw new MetaspaceException("lists can not be EMPTY");
        }
        if (CollectionUtils.isEmpty(metaDates)) {
            throw new MetaspaceException("metaDate can not be EMPTY");
        }
        if (StringUtils.isEmpty(codeProperty) || StringUtils.isEmpty(metaCode)) {
            throw new MetaspaceException("Sting param can not be EMPTY");
        }
        if (namePropertys == null || metaNames == null || namePropertys.length != metaNames.length) {
            throw new MetaspaceException("Sting[] param can not be EMPTY and Must even Length");
        }

        Object objOne = lists.get(0);
        if (Objects.isNull(objOne)) {
            throw new MetaspaceException("lists index 0 record can not be NULL");
        }
        Class<? extends Object> clazz = objOne.getClass();
        Method getReadCode = getReadCode(clazz, codeProperty);
        Method[] setWriteNames = setWriteNames(clazz, namePropertys);
        int nameNum = namePropertys.length;

        int i = 0;
        int j2 = 0;
        try {
            for (; i < lists.size(); i++) {
                Object obj = lists.get(i);
                String codeValue = (String) getReadCode.invoke(obj);
                for (int j = 0; j < metaDates.size(); j++) {
                    JSONObject metaDate = metaDates.getJSONObject(j);
                    if (codeValue.equals(metaDate.getString(metaCode))) {
                        for (j2 = 0; j2 < nameNum; j2++) {
                            setWriteNames[j2].invoke(obj, metaDate.get(metaNames[j2]));
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new MetaspaceException(
                    "lists index [" + i + "] record codeProperty [" + codeProperty + "] can not getRead or property ["
                            + namePropertys[j2] + "] can not setWrite,message is [" + e.getMessage() + "]");
        }

        return lists;
    }

    private static Method getReadCode(Class<? extends Object> clazz, String codeProperty) {
        try {
            Field fieldC = clazz.getDeclaredField(codeProperty);
            PropertyDescriptor pdc = new PropertyDescriptor(fieldC.getName(), clazz);
            return pdc.getReadMethod();// 获得读方法
        } catch (Exception e) {
            throw new MetaspaceException("can not get read code method className is [" + clazz.getName()
                    + "] codeProperty is [" + codeProperty + "],message is [" + e.getMessage() + "]");
        }
    }

    private static Method setWriteName(Class<? extends Object> clazz, String nameProperty) {
        try {
            Field fieldN = clazz.getDeclaredField(nameProperty);
            PropertyDescriptor pdc = new PropertyDescriptor(fieldN.getName(), clazz);
            return pdc.getWriteMethod();
        } catch (Exception e) {
            throw new MetaspaceException("can not get write name method className is [" + clazz.getName()
                    + "] nameProperty is [" + nameProperty + "],message is [" + e.getMessage() + "]");
        }
    }

    private static Method[] setWriteNames(Class<? extends Object> clazz, String[] namePropertys) {
        Method[] methods = new Method[namePropertys.length];
        int i = 0;
        try {
            for (i = 0; i < namePropertys.length; i++) {
                Field fieldN = clazz.getDeclaredField(namePropertys[i]);
                PropertyDescriptor pdc = new PropertyDescriptor(fieldN.getName(), clazz);
                methods[i] = pdc.getWriteMethod();
            }
            return methods;
        } catch (Exception e) {
            throw new MetaspaceException("can not get write name method className is [" + clazz.getName()
                    + "] nameProperty is [" + namePropertys[i] + "],message is [" + e.getMessage() + "]");
        }
    }

}
