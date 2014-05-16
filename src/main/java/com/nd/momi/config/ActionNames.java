package com.nd.momi.config;

/**
 *
 * @author jianying9
 */
public class ActionNames {

    public final static String UPDATE = "UPDATE";
    //-----------------------文件------------------------//
    //保存图片
    public final static String INSERT_IMAGE = "INSERT_IMAGE";
    //根据imageId查询图片数据
    public final static String INQUIRE_IMAGE_BY_KEY = "INQUIRE_IMAGE_BY_KEY";
    //-----------------------客服用户接口------------------------//
    //管理员登录
    public final static String ADMIN_LOGIN = "ADMIN_LOGIN";
    //普通用户登录
    public final static String RECEPTION_LOGIN = "RECEPTION_LOGIN";
    //登出
    public final static String RECEPTION_LOGOUT = "RECEPTION_LOGOUT";
    //新增客服用户
    public final static String INSERT_RECEPTION = "INSERT_RECEPTION";
    //查询客服用户
    public final static String INQUIRE_RECEPTION = "INQUIRE_RECEPTION";
    //删除客服用户
    public final static String DELETE_RECEPTION = "DELETE_RECEPTION";
    //分配等待队列中的客户给客服
    public final static String ALLOT_WAIT_CUSTOMER = "ALLOT_WAIT_CUSTOMER";
    //----------------------------客户接口---------------------------//
    //随机查询
    public final static String INQUIRE_CUSTOMER = "INQUIRE_CUSTOMER";
    //客户登录
    public final static String CUSTOMER_LOGIN = "CUSTOMER_LOGIN";
    //客户登出
    public final static String CUSTOMER_LOGOUT = "CUSTOMER_LOGOUT";
    //呼叫客服,开始排队
    public final static String CUSTOMER_WAIT = "CUSTOMER_WAIT";
    //-----------------------------消息------------------------------//
    //客户发送消息至客服
    public final static String SEND_MESSAGE_FROM_CUSTOMER = "SEND_MESSAGE_FROM_CUSTOMER";
    //客服发送消息至客户
    public final static String SEND_MESSAGE_FROM_RECEPTION = "SEND_MESSAGE_FROM_RECEPTION";
}
