package cn.ivfzhou.ssmexample.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;

import java.util.LinkedHashMap;
import java.util.List;

import cn.ivfzhou.ssmexample.entity.User;

/**
 * 用户表的mapper接口
 */
public interface UserMapper extends Mapper<User> {

    // 1. 查询过滤器链
    LinkedHashMap<String, String> findFilterChainDefinitionMap();

    // 2. 根据生日查询信息
    List<User> findByBirthday();

}
