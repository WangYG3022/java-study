package com.wang.weadmin.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @description: AjaxResult
 * @author: WANG Y.G.
 * @time: 2020/01/28 15:41
 * @version: V1.0
 */
public class AjaxResult extends HashMap<String, Object> implements Serializable {

    private static final boolean SUCCESS = true;
    private static final boolean ERROR = false;

    private AjaxResult() {
    }

    /**
     * 返回成功结果(含结果集)
     * @param msg 返回信息
     * @param data 结果集
     * @return AjaxResult
     */
    public static AjaxResult success(String msg, Object data) {
        AjaxResult result = new AjaxResult();
        result.put("success", SUCCESS);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    /**
     * 返回成功结果(含结果集)
     * @param data 结果集
     * @return AjaxResult
     */
    public static AjaxResult success(Object data) {
        AjaxResult result = new AjaxResult();
        result.put("success", SUCCESS);
        result.put("msg", "操作成功");
        result.put("data", data);
        return result;
    }

    /**
     * 返回成功结果
     * @param msg 返回信息
     * @return AjaxResult
     */
    public static AjaxResult success(String msg) {
        AjaxResult result = new AjaxResult();
        result.put("success", SUCCESS);
        result.put("msg", msg);
        return result;
    }

    /**
     * 返回成功结果
     * @return AjaxResult
     */
    public static AjaxResult success() {
        AjaxResult result = new AjaxResult();
        result.put("success", SUCCESS);
        result.put("msg", "操作成功");
        return result;
    }

    /**
     * 返回失败结果
     * @param msg 返回信息
     * @return AjaxResult
     */
    public static AjaxResult error(String msg) {
        AjaxResult result = new AjaxResult();
        result.put("success", ERROR);
        result.put("msg", msg);
        return result;
    }

    /**
     * 返回失败结果
     * @return AjaxResult
     */
    public static AjaxResult error() {
        AjaxResult result = new AjaxResult();
        result.put("success", ERROR);
        result.put("msg", "操作失败");
        return result;
    }
}
