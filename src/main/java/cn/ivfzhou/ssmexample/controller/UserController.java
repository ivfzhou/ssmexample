package cn.ivfzhou.ssmexample.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import cn.ivfzhou.ssmexample.entity.User;
import cn.ivfzhou.ssmexample.enums.ExceptionInfoEnum;
import cn.ivfzhou.ssmexample.exception.SsmException;
import cn.ivfzhou.ssmexample.service.UserService;
import cn.ivfzhou.ssmexample.constant.SsmConstant;
import cn.ivfzhou.ssmexample.util.SendSMSUtil;
import cn.ivfzhou.ssmexample.vo.ResultVO;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendSMSUtil sendSMSUtil;

    // 1. 转发到注册页面.
    @GetMapping("/register-ui")
    public String registerUI() {
        // 转发页面
        return "user/register";
    }

    // 2. 异步校验用户名.
    @PostMapping("/check-username")
    @ResponseBody
    public ResultVO checkUsername(@RequestBody User user) {
        // 1. 调用service判断是否可用
        userService.checkUsername(user.getUsername());
        // 2. 用户名可以使用
        return new ResultVO(0, "成功", null);
    }

    // 3. 发送手机验证码.
    @PostMapping("/send-sms")
    @ResponseBody
    public ResultVO sendSMS(String phone, HttpSession session) {
        // 1. 校验参数合法性. 正则
        if (phone == null || phone.length() != 11) {
            // 参数不合法.
            log.info("【发送短信验证】 手机号格式不正确 phone = {}", phone);
            throw new SsmException(ExceptionInfoEnum.PARAM_ERROR.getCode(), "手机号格式不正确!!!");
        }
        // 2. 调用工具类发送验证码. 拿到返回的code和msg , 将正确的验证码放到session.
        ResultVO vo = sendSMSUtil.sendSMS(phone, session);
        // 3. 响应数据.
        return vo;
    }

    // 4. 执行注册.
    @PostMapping("/register")
    @ResponseBody
    public ResultVO register(@Valid User user, BindingResult bindingResult, String registerCode, HttpSession session) {
        // 1. 校验验证码不为null,并且与session域中正确的验证码一致.
        String trueCode = (String) session.getAttribute(SsmConstant.USER_CODE);
        if (registerCode == null || !registerCode.equals(trueCode)) {
            // 验证码为空 , 验证按错误.
            log.info("【执行注册】 验证码不正确!! registerCode = {},trueCode = {}", registerCode, trueCode);
            throw new SsmException(ExceptionInfoEnum.CAPTCHA_ERROR);
        }
        // 2. 通过bindingResult判断用户名,密码,手机号是否合法.
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            log.info("【执行注册】 参数不合法 , msg = {},user = {}", msg, user);
            throw new SsmException(ExceptionInfoEnum.PARAM_ERROR.getCode(), msg);
        }
        // 3. 调用service执行注册.
        userService.register(user);
        // 4. 响应数据.
        return new ResultVO(0, "成功", null);
    }

    // 5. 跳转到登录页面
    @GetMapping("/login-ui")
    public String loginUI() {
        return "user/login";
    }

    // 6. 执行登录.
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(String username, String password, HttpSession session) {
        // 0. 接收参数,并手动校验参数.
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            log.info("【登录功能】 参数不合法!! username = {},password = {}", username, password);
            throw new SsmException(ExceptionInfoEnum.PARAM_ERROR.getCode(), "用户名和密码均为必填项,岂能不填!");
        }
        /*
        // 3. 调用service执行登录. -> 接收到用户信息.
        User user = userService.login(username, password);
        // 4. 将用户信息设置到session域中.
        session.setAttribute(USER_INFO,user);*/
        // 1. 获取subject
        Subject subject = SecurityUtils.getSubject();
        // 2. 由主体提交认证请求
        try {
            subject.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            throw new SsmException(ExceptionInfoEnum.USER_USERNAME_ERROR);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            throw new SsmException(ExceptionInfoEnum.USER_PASSWORD_ERROR);
        }
        // 5. 响应数据.
        return new ResultVO(0, "成功", null);
    }
}
