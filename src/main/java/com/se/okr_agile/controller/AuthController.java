package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.service.AuthService;
import com.se.okr_agile.vo.LoginRequestVO;
import com.se.okr_agile.vo.LoginVO;
import com.se.okr_agile.vo.RegisterRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public Result register(@RequestBody RegisterRequestVO registerRequestVO) {
        try {
            authService.register(registerRequestVO);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public Result<LoginVO> login(@RequestBody LoginRequestVO loginRequestVO) {
        try {
            LoginVO loginVO = authService.login(loginRequestVO);
            return Result.success(loginVO);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/auth/logout")
    public Result logout(@RequestBody String token) {
        try {
            authService.logout(token);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
