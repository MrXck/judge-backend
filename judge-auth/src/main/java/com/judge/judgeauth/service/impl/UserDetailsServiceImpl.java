package com.judge.judgeauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.judge.judgeauth.dto.AuthParamsDto;
import com.judge.judgeauth.mapper.UserMapper;
import com.judge.judgeauth.pojo.UserPermission;
import com.judge.judgeauth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplicationContext applicationContext;


    //传入的请求认证的参数就是AuthParamsDto
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //将传入的json转成AuthParamsDto对象
        AuthParamsDto authParamsDto;
        try {
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        } catch (Exception e) {
            throw new RuntimeException("请求认证参数不符合要求");
        }

        //认证类型，有password，wx。。。
        String authType = authParamsDto.getAuthType();


        //根据认证类型从spring容器取出指定的bean
        String beanName = authType + "_authservice";
        AuthService authService = applicationContext.getBean(beanName, AuthService.class);
        //调用统一execute方法完成认证
        UserPermission userPermission = authService.execute(authParamsDto);
        //封装xcUserExt用户信息为UserDetails
        //根据UserDetails对象生成令牌

        return getUserPrincipal(userPermission);
    }


    public UserDetails getUserPrincipal(UserPermission user) {
        String password = user.getPassword();
        //权限
        String[] authorities;
        //根据用户id查询用户的权限
        List<String> permissions = userMapper.getPermissions(user.getId());
        authorities = permissions.toArray(new String[0]);

        user.setPassword(null);
        //将用户信息转json
        String userJson = JSON.toJSONString(user);
        return User.withUsername(userJson).password(password).authorities(authorities).build();
    }


}
