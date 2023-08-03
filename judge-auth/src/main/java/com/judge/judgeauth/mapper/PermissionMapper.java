package com.judge.judgeauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judge.judgeauth.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}