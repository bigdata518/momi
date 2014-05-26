package com.nd.momi.config;

/**
 *
 * @author cy
 */
public class ResponseFlags {

    //用户id不存在
    public final static String FAILURE_ID_NOT_EXIST = "FAILURE_ID_NOT_EXIST";
    //密码错误
    public final static String FAILURE_PASSWORD_ERROR = "FAILURE_PASSWORD_ERROR";
    //id已经被使用
    public final static String FAILURE_ID_USED = "FAILURE_ID_USED";
    //游戏不存在
    public final static String FAILURE_NO_GAME = "FAILURE_NO_GAME";
    //提交的数据异常
    public final static String FAILURE_ERROR_DATA = "FAILURE_ERROR_DATA";
}
