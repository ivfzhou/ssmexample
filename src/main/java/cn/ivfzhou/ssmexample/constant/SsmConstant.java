package cn.ivfzhou.ssmexample.constant;

public interface SsmConstant {

    // 用户发送短信正确的验证码存放在session中的key
    String USER_CODE = "user-code";

    // 密码加密除数
    int HASH_ITERATIONS = 1024;

    // 设置到session域中的用户信息
    String USER_INFO = "USER_INFO";

}
