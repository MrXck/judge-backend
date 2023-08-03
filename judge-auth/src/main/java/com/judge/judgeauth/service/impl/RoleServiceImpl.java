package com.judge.judgeauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judge.judgeauth.mapper.RoleMapper;
import com.judge.judgeauth.pojo.Role;
import com.judge.judgeauth.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}