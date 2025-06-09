package com.bryce.metaspace.api.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 公共响应类.
 * 
 * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2017-12-04.
 */

@Data
@NoArgsConstructor
public class CommonResponse <T> implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 694782754779459320L;
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回码说明
     */
    private String message;
    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 返回码、返回信息映射
     */
    public static final Map<String, String> RESPONSE_MAP = new HashMap<String, String>();

    
    public CommonResponse(String code) {
        this.code =code;
        this.message=RESPONSE_MAP.get(code);
    }
    
    /**
     * 根据错误码和返回结果组装返回码、返回信息和返回结果
     * @param code
     * @param data
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2020年7月9日.
     */
    public CommonResponse(String code,T data) {
        this.code =code;
        this.message=RESPONSE_MAP.get(code);
        this.data = data;
    }

    /**
     * 根据错误码、返回信息和返回结果组装返回码、返回信息和返回结果
     * @param code
     * @param desc
     * @param data
     *
     * Created by Bryce Yao<sysyaoyulong@gmail.com> on 2020年7月9日.
     */
    public CommonResponse(String code,String message,T data) {
        this.code =code;
        this.message=message;
        this.data = data;
    }
    
    /**
     * 判断请求是否成功
     *
     * @return 请求是否成功的结果
     */
    public static boolean isSuccess(CommonResponse<?> response) {
        if (response == null) {
            return false;
        }
        return ResponseCodeTypeEnum.SUCCESS.getCode().equals(response.getCode());
    }

}
