package cn.ivfzhou.ssmexample.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ivfzhou.ssmexample.entity.User;
import cn.ivfzhou.ssmexample.service.UserService;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 设置MD5加密1024次.
    {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        this.setCredentialsMatcher(matcher);
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1. 获取用户输入的用户名
        String username = (String) token.getPrincipal();
        // 2. 根据用户名查询用户信息. (userService) (模拟数据库操作)
        User user = userService.findByUsername(username);
        // 3. 判断用户信息是否为null -> 直接返回true
        if (user == null) {
            return null;
        }
        // 4. 将正确的user对象和密码封装到AuthenticationInfo对象中
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), "嘿嘿嘿");
        // 5. 将盐设置到info对象中
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        // 6. 返回AuthenticationInfo对象.
        return info;
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*System.out.println("去数据库查询权限信息!!");
        // 1. 获取当前登录用户的用户名.
        User user = (User) principals.getPrimaryPrincipal();
        String username = user.getUsername();
        // 2. 根据用户名查询全部的角色
        Set<String> roleNames = userService.findRoleNamesByUsername(username);
        // 3. 根据角色查询全部的权限
        Set<String> permissions = userService.findPermissionsByRoleNames(roleNames);
        // 4. 创建返回结果info，并封装角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        // 5. 返回
        return info;*/
        return null;
    }

}
