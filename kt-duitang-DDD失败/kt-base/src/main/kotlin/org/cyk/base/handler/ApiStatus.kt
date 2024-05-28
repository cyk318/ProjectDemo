package org.cyk.base.handler
/**
 * 用来表明 api 的不同状态
 */
enum class ApiStatus(val code: Int, val msg: String) {
    //请求成功
    SUCCESS(0, "ok"),

    //请求失败
    FAIL(1, "request failed"),

    //非法请求
    INVALID_REQUEST(2, "invalid request"),

    //非法参数
    INVALID_PARAM(3, "invalid param"),

    //未绑定
    NOT_BINDED(4, "not binded"),

    //服务器错误
    SERVER_ERROR(5, "server error"),

    //没有注册
    NOT_REGISTERED(6, "not registered"),

    //token 过期
    TOKEN_EXPIRE(7, "token expire"),

    //token 非法
    TOKEN_INVALID (8, "token invalid"),

    //没有权限
    NOT_AUTHORIZED (9, "not authorized"),

    //用户未登录
    USER_NOT_LOGIN(1001, "user not login"),

    //用户名或密码错误
    USERNAME_OR_PASSWORD_ERROR(1002, "username or password error"),

    //用户名已存在
    USERNAME_EXISTS(1003, "username exists"),

    //用户名不存在
    USERNAME_NOT_EXISTS(1004, "username not exists"),

    //用户不存在
    USER_NOT_EXISTS(1005, "user not exists"),
}
