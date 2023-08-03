package com.judge.judgeauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judge.judgeauth.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT rp.key FROM rbac_user_role ur LEFT JOIN rbac_role rr ON ur.role_id = rr.id LEFT JOIN rbac_role_permissions rrp on rr.id = rrp.role_id LEFT JOIN rbac_permission rp on rp.id = rrp.permission_id WHERE ur.user_id = #{userId}")
    List<String> getPermissions(@Param("userId") Long userId);
}
