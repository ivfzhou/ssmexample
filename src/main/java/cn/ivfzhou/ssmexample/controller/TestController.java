package cn.ivfzhou.ssmexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/xxx")
public class TestController {

    @GetMapping("/xxx")
    @ResponseBody
    public Map<String, String> xxx() {
        Map<String, String> map = new HashMap<>();
        map.put("xxx", "xxx");
        return map;
    }

}
