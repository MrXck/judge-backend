package com.judge.judgeauth.service;


import com.judge.judgeauth.dto.AuthParamsDto;
import com.judge.judgeauth.pojo.UserPermission;

/**
 * @author Mr.M
 * @version 1.0
 * @description 统一的认证接口
 * @date 2023/2/24 11:55
 */
public interface AuthService {

    /**
     * @param authParamsDto 认证参数
     * @return com.xuecheng.ucenter.model.po.XcUser 用户信息
     * @description 认证方法
     * @author Mr.M
     * @date 2022/9/29 12:11
     */
    UserPermission execute(AuthParamsDto authParamsDto);

}
