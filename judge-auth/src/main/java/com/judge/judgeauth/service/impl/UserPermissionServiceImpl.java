package com.judge.judgeauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judge.judgeauth.mapper.UserPermissionMapper;
import com.judge.judgeauth.pojo.UserPermission;
import com.judge.judgeauth.service.UserPermissionService;
import org.springframework.stereotype.Service;

@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements UserPermissionService {
}