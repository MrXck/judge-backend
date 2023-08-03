package com.judge.judgeauth.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xck
 */
@Data
public class User {

    private Long id;
    private String username;

    private String password;
    private String avatar;

    private String phone;
    private String nickname;
    private Integer sex;
    private String wxUnionid;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
