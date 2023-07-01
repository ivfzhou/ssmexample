package cn.ivfzhou.ssmexample.service;

import java.util.LinkedHashMap;
import java.util.List;

import cn.ivfzhou.ssmexample.entity.User;

/**
 * 用户模块的service接口
 */
public interface UserService {

    // 1. 校验用户名是否可用
    void checkUsername(String username);

    // 2. 执行注册
    void register(User user);

    // 3. 执行登录
    User login(String username, String password);

    // 4. shiro认证,根据用户名查询用户信息
    User findByUsername(String username);

    // 5. 查询过滤器链的信息
    LinkedHashMap<String, String> findFilterChainDefinitionMap();

    // 6. 查询今天过生日的用户
    List<User> findTodayPassBirthday();

}
