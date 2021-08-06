package com.mrqinzh.realm;

import com.mrqinzh.entity.User;
import com.mrqinzh.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //System.out.println("执行了授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前登录对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal(); // 获取user对象

        //设置数据库中权限字段
        info.addStringPermission(user.getPerms());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //System.out.println("执行了认证");

        //通过参数获取登录的控制器中生成的 令牌
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //设置用户、密码
        //数据库中获取
        User user = userService.logByUidEmailTel(token.getUsername());
        //用户认证
        if (user == null){   //没有这个人
            return null;
        }
        //密码认证
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), "");
    }
}
