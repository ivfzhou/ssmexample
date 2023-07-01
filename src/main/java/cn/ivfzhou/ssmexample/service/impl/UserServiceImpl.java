package cn.ivfzhou.ssmexample.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import cn.ivfzhou.ssmexample.constant.SsmConstant;
import cn.ivfzhou.ssmexample.entity.User;
import cn.ivfzhou.ssmexample.enums.ExceptionInfoEnum;
import cn.ivfzhou.ssmexample.exception.SsmException;
import cn.ivfzhou.ssmexample.mybatis.mapper.UserMapper;
import cn.ivfzhou.ssmexample.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 异步校验用户名
    @Override
    public void checkUsername(String username) {
        // 1. 封装查询条件
        User param = new User();
        param.setUsername(username);
        // 2. 调用userMapper查询
        int count = userMapper.selectCount(param);
        // 3. 判断返回结果
        if (count != 0) {
            // 用户名不可用
            log.info("【异步校验用户名】 用户名不可用!!! username = {}", username);
            throw new SsmException(11, "用户名不可用!!!");
        }
    }

    // 注册
    @Override
    @Transactional
    public void register(User user) {
        // 1. 生成一个盐
        String salt = UUID.randomUUID().toString();
        // 2. 对密码进行md5加密和加盐 -> shiro-core-1.4.0
        String newPassword = new Md5Hash(user.getPassword(), salt, SsmConstant.HASH_ITERATIONS).toString();
        //  3. 封装数据
        user.setSalt(salt);
        user.setPassword(newPassword);
        // 4. 执行添加
        int count = userMapper.insertSelective(user);
        // 5. 判断注册是否成功
        if (count != 1) {
            log.error("【注册账号】 注册账号失败 user = {}", user);
            throw new SsmException(ExceptionInfoEnum.USER_REGISTER_ERROR);
        }
    }

    // 登录
    @Override
    public User login(String username, String password) {
        // 1. 根据用户名查询用户信息
        User param = new User();
        param.setUsername(username);
        User user = userMapper.selectOne(param);
        // 2. 如果用户信息为null -> 用户名不正确
        if (user == null) {
            log.info("【登录功能】 用户名不存在! username = {}", username);
            throw new SsmException(ExceptionInfoEnum.USER_USERNAME_ERROR);
        }
        // 3. 将用户输入的密码进行加密和加盐
        String newPassword = new Md5Hash(password, user.getSalt(), SsmConstant.HASH_ITERATIONS).toString();
        // 4. 判断密码是否正确(不正确) -> 密码不正确
        if (!user.getPassword().equals(newPassword)) {
            log.info("【登录功能】 密码错误! user = {},username = {},password = {}", user, username, password);
            throw new SsmException(ExceptionInfoEnum.USER_PASSWORD_ERROR);
        }
        // 5. 返回用户信息
        return user;
    }

    @Override
    public User findByUsername(String username) {
        // 1. 封装参数
        User param = new User();
        param.setUsername(username);
        // 2. 执行查询
        User user = userMapper.selectOne(param);
        // 3. 返回user对象
        return user;
    }

    @Override
    public LinkedHashMap<String, String> findFilterChainDefinitionMap() {
        return userMapper.findFilterChainDefinitionMap();
    }

    @Override
    public List<User> findTodayPassBirthday() {
        return userMapper.findByBirthday();
    }

}
