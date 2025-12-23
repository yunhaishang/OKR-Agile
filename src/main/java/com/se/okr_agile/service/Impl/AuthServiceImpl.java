package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.User;
import com.se.okr_agile.mapper.UserMapper;
import com.se.okr_agile.service.AuthService;
import com.se.okr_agile.service.UserService;
import com.se.okr_agile.util.JwtUtil;
import com.se.okr_agile.util.PasswordUtil;
import com.se.okr_agile.vo.LoginRequestVO;
import com.se.okr_agile.vo.LoginVO;
import com.se.okr_agile.vo.RegisterRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    @Transactional
    public void register(RegisterRequestVO registerRequestVO) {
        String username = registerRequestVO.getUsername();
        String email = registerRequestVO.getEmail();

        // 验证账号
        if(isUsernameExists(username)) {
            throw new RuntimeException("用户名已存在");
        }

        // 验证邮箱
        if(isEmailExists(email)) {
            throw new RuntimeException("邮箱已存在");
        }

        // 创建插入对象
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole("member");
        user.setPassword(passwordUtil.encode(registerRequestVO.getPassword()));

        // 插入
        this.save(user);
    }

    @Override
    @Transactional
    public LoginVO login(LoginRequestVO loginRequestVO) {
        String username = loginRequestVO.getUsername();
        String password = loginRequestVO.getPassword();

        // 查用户
        User user = userService.lambdaQuery()
                .eq(User::getUsername, username)
                .one();

        if (user == null) {
            throw new RuntimeException("用户名错误");
        }

        // 校验密码
        if (!passwordUtil.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 生成 token 并插入到数据库
        String jwt = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 构建返回对象
        LoginVO loginVO = new LoginVO();
        loginVO.setUsername(username);
        loginVO.setToken(jwt);

        // 返回
        return loginVO;
    }

    @Override
    @Transactional
    public void logout() {

    }

    public boolean isUsernameExists(String username) {
        return this.lambdaQuery()
                .eq(User::getUsername, username)
                .exists();
    }

    public boolean isEmailExists(String email) {
        return this.lambdaQuery()
                .eq(User::getEmail, email)
                .exists();
    }
}
