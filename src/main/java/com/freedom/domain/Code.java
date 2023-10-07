package com.freedom.domain;

public class Code {
    //数据层状态码
    public static final Integer SAVE_OK = 500;//数据库保存数据成功
    public static final Integer DELETE_OK = 510;//删除数据成功
    public static final Integer UPDATE_OK = 520;//更新数据成功
    public static final Integer GET_OK = 530;//获取数据成功

    public static final Integer SAVE_ERROR = 501;//数据库保存数据失败
    public static final Integer DELETE_ERROR = 511;//删除数据失败
    public static final Integer UPDATE_ERROR = 521;//更新数据失败
    public static final Integer GET_ERROR = 531;//获取数据失败
    //Excel表格读取失败
    public static final Integer UPLOAD_OK = 600;//Excel上传成功
    public static final Integer DOWNLOAD_OK = 610;//Excel下载成功
    public static final Integer UPLOAD_ERROR = 601;//Excel上传失败
    public static final Integer DOWNLOAD_ERROR = 611;//Excel下载失败
    //业务层状态码
    public static final Integer SYSTEM_UNKNOW_ERROR = 1001;//系统未知错误
    public static final Integer BUSINESS_ERROR = 1011;//业务问题错误
    public static final Integer SYSTEM_TIMEOUT = 1021;//系统超时错误
    public static final Integer SYSTEM_ERROR = 1031;//系统错误
    public static final Integer IO_ERROR = 1041;//文件的输入输出错误


}
