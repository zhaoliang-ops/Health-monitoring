package org.gv_data.hmt.model;

public class MsgConstants {
    public static final Integer DELIVERING = 0;//消息投递中
    public static final Integer SUCCESS = 1;//消息投递成功
    public static final Integer FAILURE = 2;//消息投递失败
    public static final Integer MAX_TRY_COUNT = 3;//最大重试次数
    public static final Integer MSG_TIMEOUT = 1;//消息超时时间
    public static final String MSG_QUEUE_NAME = "SubSystemLogQueue";
    public static final String MSG_EXCHANGE_NAME = "SubSystemLogExchange";
    public static final String MSG_ROUTING_KEY_NAME = "SubSystemLogKey";
}
