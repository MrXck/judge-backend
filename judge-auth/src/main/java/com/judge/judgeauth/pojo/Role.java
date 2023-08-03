package com.judge.judgeauth.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rbac_role")
public class Role {

    private Long id;
    private String name;
    private String roleKey;
}
