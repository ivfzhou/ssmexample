package cn.ivfzhou.ssmexample.factory;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;

import cn.ivfzhou.ssmexample.service.UserService;

public class FilterChainDefinitionMapFactory {

    @Autowired
    private UserService userService;

    public LinkedHashMap<String, String> getInstance() {
        return userService.findFilterChainDefinitionMap();
    }

}
