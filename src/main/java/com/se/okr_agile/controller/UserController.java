package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.service.UserService;
import com.se.okr_agile.vo.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/me")
    public Result<UserInfoVO> getUserInfo(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            UserInfoVO userInfoVO = userService.getUserInfo(userId);
            return Result.success(userInfoVO);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
