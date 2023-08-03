package com.judge.judgeauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.judge.judgeauth.dto.AuthParamsDto;
import com.judge.judgeauth.mapper.UserMapper;
import com.judge.judgeauth.pojo.User;
import com.judge.judgeauth.pojo.UserPermission;
import com.judge.judgeauth.service.AuthService;
import com.judge.judgeauth.service.WxAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@Service("wx_authservice")
public class WxAuthServiceImpl implements AuthService, WxAuthService {

    @Autowired
    UserMapper userMapper;

    //@Autowired
    //XcUserRoleMapper xcUserRoleMapper;

    @Autowired
    WxAuthServiceImpl currentProxy;

    @Autowired
    RestTemplate restTemplate;

    @Value("${weixin.appid}")
    String appid;

    @Value("${weixin.secret}")
    String secret;

    @Override
    public UserPermission execute(AuthParamsDto authParamsDto) {
        //得到账号
        String username = authParamsDto.getUsername();
        //查询数据库
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        UserPermission userPermission = new UserPermission();
        BeanUtils.copyProperties(user, userPermission);

        return userPermission;
    }

    @Override
    public User wxAuth(String code) {
        //申请令牌
        Map<String, String> access_token_map = getAccess_token(code);
        //访问令牌
        String access_token = access_token_map.get("access_token");
        String openid = access_token_map.get("openid");

        //携带令牌查询用户信息
        Map<String, String> userinfo = getUserinfo(access_token, openid);

        // 保存用户信息到数据库

        return currentProxy.addWxUser(userinfo);
    }

    @Transactional
    public User addWxUser(Map<String, String> userInfo_map) {
        String unionid = userInfo_map.get("unionid");
        String nickname = userInfo_map.get("nickname");
        //根据unionid查询用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getWxUnionid, unionid));
        if (user != null) {
            return user;
        }
        //向数据库新增记录
        user = new User();
        user.setUsername(unionid);
        user.setPassword(unionid);
        user.setNickname(nickname);
        user.setCreateTime(LocalDateTime.now());
        //插入
        int insert = userMapper.insert(user);

        //向用户角色关系表新增记录
        //XcUserRole xcUserRole = new XcUserRole();
        //xcUserRole.setId(UUID.randomUUID().toString());
        //xcUserRole.setUserId(userId);
        //xcUserRole.setRoleId("17");//学生角色
        //xcUserRole.setCreateTime(LocalDateTime.now());
        //xcUserRoleMapper.insert(xcUserRole);
        return user;

    }

    /**
     * 携带授权码申请令牌
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     * <p>
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE",
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     *
     * @param code 授权
     * @return
     */
    private Map<String, String> getAccess_token(String code) {

        String url_template = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        //最终的请求路径
        String url = String.format(url_template, appid, secret, code);

        //远程调用此url
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        //获取响应的结果
        String result = exchange.getBody();
        //将result转成map
        Map<String, String> map = JSON.parseObject(result, Map.class);
        return map;


    }

    /**
     * 携带令牌查询用户信息
     * <p>
     * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
     * <p>
     * {
     * "openid":"OPENID",
     * "nickname":"NICKNAME",
     * "sex":1,
     * "province":"PROVINCE",
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl": "https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     * "privilege":[
     * "PRIVILEGE1",
     * "PRIVILEGE2"
     * ],
     * "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * <p>
     * }
     *
     * @param access_token
     * @param openid
     * @return
     */
    private Map<String, String> getUserinfo(String access_token, String openid) {

        String url_template = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
        String url = String.format(url_template, access_token, openid);

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        //获取响应的结果
        String result = new String(exchange.getBody().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        //将result转成map
        Map<String, String> map = JSON.parseObject(result, Map.class);
        return map;

    }
}
