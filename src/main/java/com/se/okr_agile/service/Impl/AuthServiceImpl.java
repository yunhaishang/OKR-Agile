package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.Token;
import com.se.okr_agile.entity.User;
import com.se.okr_agile.mapper.TokenMapper;
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
    private TokenMapper tokenMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    @Transactional
    public void register(RegisterRequestVO registerRequestVO) {
        // 验证账号
        if(isUsernameExists(registerRequestVO.getUsername())) {
            throw new RuntimeException("账号已存在");
        }

        // 验证邮箱
        if(isEmailExists(registerRequestVO.getEmail())) {
            throw new RuntimeException("邮箱已存在");
        }

        // 创建插入对象
        User user = new User();
        user.setUsername(registerRequestVO.getUsername());
        user.setPassword(passwordUtil.encode(registerRequestVO.getPassword()));
        user.setEmail(registerRequestVO.getEmail());

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
            throw new RuntimeException("用户名或密码错误");
        }

        // 校验密码
        if (!passwordUtil.matches(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 生成 token 并插入到数据库
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Token tokenInDb = new Token();
        tokenInDb.setToken(token);
        tokenMapper.insert(tokenInDb);

        // 构建返回对象
        LoginVO loginVO = new LoginVO();
        loginVO.setUsername(username);
        loginVO.setToken(token);

        return loginVO;
    }

    @Override
    @Transactional
    public void logout(String token) {
        tokenMapper.deleteByToken(token);
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
