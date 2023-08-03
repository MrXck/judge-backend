package com.judge.judgeauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.judge.judgeauth.dto.UserRegisterDTO;
import com.judge.judgeauth.pojo.User;

public interface UserService extends IService<User> {

    void register(UserRegisterDTO userRegisterDTO);

}