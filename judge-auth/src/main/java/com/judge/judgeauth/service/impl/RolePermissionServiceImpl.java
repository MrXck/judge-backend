package com.judge.judgeauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judge.judgeauth.mapper.RolePermissionMapper;
import com.judge.judgeauth.pojo.RolePermission;
import com.judge.judgeauth.service.RolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}