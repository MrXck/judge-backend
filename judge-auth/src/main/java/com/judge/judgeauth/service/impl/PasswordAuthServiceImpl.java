package com.judge.judgeauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.judge.judgeauth.dto.AuthParamsDto;
import com.judge.judgeauth.mapper.UserMapper;
import com.judge.judgeauth.pojo.User;
import com.judge.judgeauth.pojo.UserPermission;
import com.judge.judgeauth.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service("password_authservice")
public class PasswordAuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserPermission execute(AuthParamsDto authParamsDto) {
        //账号
        String username = authParamsDto.getUsername();

        //账号是否存在
        //根据username账号查询数据库
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));

        //查询到用户不存在，要返回null即可，spring security框架抛出异常用户不存在
        if (user == null) {
            throw new RuntimeException("账号不存在");
        }

        //验证密码是否正确
        //如果查到了用户拿到正确的密码
        String passwordDb = user.getPassword();
        //拿 到用户输入的密码
        String passwordForm = authParamsDto.getPassword();


        //校验密码
        boolean matches = passwordEncoder.matches(passwordForm, passwordDb);
        if (!matches) {
            throw new RuntimeException("账号或密码错误");
        }
        UserPermission userPermission = new UserPermission();
        BeanUtils.copyProperties(user, userPermission);

        return userPermission;
    }
}
