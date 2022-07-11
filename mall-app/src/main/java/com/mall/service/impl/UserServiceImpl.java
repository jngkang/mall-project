package com.mall.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import com.mall.entity.User;
import com.mall.dao.UserDao;
import com.mall.model.UserLoginDTO;
import com.mall.model.UserRegisterDTO;
import com.mall.query.UserQuery;
import com.mall.service.UserService;
import com.mall.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JngKang
 * @date 2022-06-28 16:01
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private EmailUtil emailUtil;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${token.key}")
    private String tokenKey;

    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        // 根据前端传回的数据中的id进行查询数据
        List<UserLoginDTO> queryRes = userDao.select(UserQuery.builder().username(userLoginDTO.getUsername()).password(userLoginDTO.getPassword()).build());
        UserLoginDTO res = queryRes.get(0);
        // TODO 此处从数据库中获取盐值，再使用MD5加密后再进行比较
        if (!(userLoginDTO.getUsername().equals(res.getUsername())
                && userLoginDTO.getPassword().equals(res.getPassword()))) {
            throw new Exception("用户不存在");
        }
        //生成token
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                // token中所包含的信息
                put("id", res.getId());
                put("username", res.getUsername());
                put("nickname", res.getNickname());
                // token的生存周期
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 2);
            }
        };
        // 根据指定的tokenKey进行生成token
        String token = JWTUtil.createToken(map, this.tokenKey.getBytes());

        return token;
    }

    @Override
    public String register(UserRegisterDTO userRegisterDTO) throws Exception {
        String emailCaptcha = stringRedisTemplate.opsForValue().get("captcha#" + userRegisterDTO.getEmail());
        if (StrUtil.isBlank(emailCaptcha)) {
            throw new Exception("验证码输入有误！");
        }
        if (!emailCaptcha.equals(userRegisterDTO.getEmailCaptcha())) {
            throw new Exception("验证码输入有误！");
        }
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getPassword2())) {
            throw new Exception("确认密码有误！");
        }
        User user = User.builder()
                .username(userRegisterDTO.getUsername())
                .email(userRegisterDTO.getEmail())
                .password(userRegisterDTO.getPassword())
                .build();
        // TODO 此处使用MD5进行加密，并保存盐值
        Integer res = userDao.insert(user);
        return "ok";
    }

    public String sendEmailCaptcha(String email) throws Exception {
        // 检测邮箱是否合法
        final String emailRegex = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new Exception("该邮箱不合法，请更换邮箱！");
        }
        // 检测邮箱是否已经被注册
        List<UserLoginDTO> list = userDao.select(UserQuery.builder().email(email).build());
        if (list.size() > 0) {
            throw new Exception("该邮箱已经被绑定，请更换邮箱！");
        }
        // 先验证Redis中是否已经存在验证码，防止通过接口攻击
        if (StrUtil.isNotBlank(stringRedisTemplate.opsForValue().get("captcha#" + email))) {
            throw new Exception("验证码已经发送，请勿重复发送！");
        }
        // 开启多线程发送邮件
        new Thread(() -> {
            int code = RandomUtil.randomInt(100000, 999999);
            emailUtil.send(Collections.singletonList(email), "验证码", "您的验证码为：" + code, false);
            // 发送成功后存入Redis
            stringRedisTemplate.opsForValue().set("captcha#" + email, code + "", 5, TimeUnit.MINUTES);
        }).start();
        return "ok";
    }

}
