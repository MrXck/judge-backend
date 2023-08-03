package com.judge.judgeauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judge.judgeauth.mapper.UserRoleMapper;
import com.judge.judgeauth.pojo.UserRole;
import com.judge.judgeauth.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}