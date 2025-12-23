package com.se.okr_agile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.okr_agile.entity.User;
import com.se.okr_agile.vo.LoginRequestVO;
import com.se.okr_agile.vo.LoginVO;
import com.se.okr_agile.vo.RegisterRequestVO;

public interface AuthService extends IService<User> {
    void register(RegisterRequestVO registerRequestVO);

    LoginVO login(LoginRequestVO loginRequestVO);

    void logout();
}
