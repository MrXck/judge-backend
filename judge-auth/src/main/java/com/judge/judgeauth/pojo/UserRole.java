package com.judge.judgeauth.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rbac_user_role")
public class UserRole {

    private Long id;
    private Long userId;
    private Long roleId;
}
