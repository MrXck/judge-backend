package com.judge.judgeauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judge.judgeauth.mapper.PermissionMapper;
import com.judge.judgeauth.pojo.Permission;
import com.judge.judgeauth.service.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}