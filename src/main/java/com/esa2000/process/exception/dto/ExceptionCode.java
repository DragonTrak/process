package com.esa2000.process.exception.dto;

public class ExceptionCode {

    public static  final int SUCCESS = 0;
    //parameter Exception
    public static final int EMPTY_PARAMETER = 1001; //必填参数为空
    public static final int INVALID_PARAMETER = 1002;//无效参数
    //Connection Exception
    public static final int CONNECT_FAILED = 1101; //无法连接服务端
    //Authorization Exception
    public static final int INCORRET_AUTHORIZTION_INFOMATION = 1200;//认证信息错误
    public static final int NOT_ENOUGH_TIME = 1201;//授权次数不足
    public static final int NOT_ENGOUGH_ROOM= 1202;//授权空间不足
    public static final int NOT_ENGOUGH= 1203;//请增加授权后使用接口。

    // BQService exception
    public static final int PLATA_CERT_ERROR=1305;//解析平台证书失败
    public static final int PLATA_CERT_PATH_ERROR=1306;//加载证书报错，请检查平台签名证书是否存在！配置是否正确
    public static final int ZZTOOL_UNPACKBOX_ERROR = 1308;//解包过程出现错误，无法正常解密
    public static final int ZZTOOL_PACKBOX_ERROR = 1309;//打包过程出现错误，无法正常返回数据
    //Client exception
    public static final int OSS_DOWNLOAD_ERROR=1404;//{0}下载失败
    //Business exception
    public static final int LOG_LOAD_DATABASE_ERROR=1501;//日志保全失败,数据库保存异常
    public static final int FILE_LOAD_DATABASE_ERROR=1502;//原文件保全失败,数据库保存异常
    public static final int FILE_HASH_LOAD_DATABASE_ERROR=1503;//文件摘要保全失败,数据库保存异常
    public static final int FILE_LOAD_ERROR=1504;//原文件保全失败
    public static final int SERVER_ERROR =1505;//服务内部异常
    public static final int LOG_LOAD_ERROR=1506;//日志上传失败
    public static final int QUERY_NO_RESULT=1508;//{0}等查询条件下，查询结果为空
    public static final int DIGEST_ALGORITHMS_ERROR =1509;//数据摘要运算错误
    public static final int VERIFY_SAFE_ID_NO_RESULT =1511;//数据验证失败,{0}信息不存在
    public static final int VERIFY_SAFE_MISMATCH =1512;//数据验证失败,不存在匹配的原文摘要
    public static final int DOWNlOAD_FILE_ERROR =1513;//文件下载失败,{0}查询无结果
    public static final int DOWNlOAD_FILE_PATH_ERROR =1514;//{0}文件不存在，无法下载
    public static final int APPLY_CERT_ERROR=1516;//申请出证失败
    public static final int ORG_CERT_AUTH_NO_EXIST=1517;//存证单位不存在
    public static final int ORG_CERT_SERVICE_URL_ERROR=1518;//存证服务地址为空
    public static final int SYNC_DATA_ERROR=1519;//同步数据异常

    public static final int PAGE_ERROR_FILE_ISNOTEXIST=1601;//文件不存在，无法下载
    public static final int PAGE_ERROR_FILE_CORRUPTED=1602;//文件损坏，无法正常下载

    public static final int SYNC_USER_ACCOUNT_EXIST=1603;//用户编号已经存在;
    public static final int SYNC_ORG_ID_EXIST=1604;//机构编号已经存在;
    public static final int SAFE_PLATAS_SAVE_ERROR=1605;//平台应用保存异常;



}
