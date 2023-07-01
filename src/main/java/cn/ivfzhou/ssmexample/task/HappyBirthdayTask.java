package cn.ivfzhou.ssmexample.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import cn.ivfzhou.ssmexample.entity.User;
import cn.ivfzhou.ssmexample.service.UserService;
import cn.ivfzhou.ssmexample.util.EmailUtils;

@Component
public class HappyBirthdayTask {

    @Autowired
    private UserService userService;

    public void sendBirthdayEmail() {
        // 1. 去数据库查询过生日的用户
        // select * from user where DATE_FORMAT(birthday,'%m-%d') = DATE_FORMAT(now(),'%m-%d');
        List<User> userList = userService.findTodayPassBirthday();
        // 2. 判断是否有人过.
        if (userList != null && userList.size() > 0) {
            // 3. 给每一位过生日的用户发送邮件.
            for (User user : userList) {
                EmailUtils.sendMessage(user);
            }
        }
    }

}
