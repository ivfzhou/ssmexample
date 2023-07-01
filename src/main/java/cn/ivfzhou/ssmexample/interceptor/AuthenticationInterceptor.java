package cn.ivfzhou.ssmexample.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ivfzhou.ssmexample.constant.SsmConstant;
import cn.ivfzhou.ssmexample.entity.User;

// 认证拦截器
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("处理1");
        // 手动判断路径.
        // String uri = request.getRequestURI();
        // if(uri.contains("/user/")){
        //     return true;
        // }
        // 1. 通过request获取session.
        HttpSession session = request.getSession();
        // 2. 通过session获取登录后的用户信息.
        User user = (User) session.getAttribute(SsmConstant.USER_INFO);
        // 3.1 如果用户信息为null -> 重定向到登录页面.
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login-ui");
            return false;
        } else {
            // 3.2 如果用户信息不为null -> 放行.
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("处理2");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("处理3");
    }

}
