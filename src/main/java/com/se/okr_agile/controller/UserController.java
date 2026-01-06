package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.User;
import com.se.okr_agile.service.UserService;
import com.se.okr_agile.vo.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public Result<UserInfoVO> getUserInfo(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            UserInfoVO userInfoVO = userService.getUserInfo(userId);
            return Result.success(userInfoVO);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping
    public Result<List<User>> getUsers() {
        try {
            List<User> userList = userService.list();
            return Result.success(userList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getById(id);
            if (user == null) {
                return Result.error("User not found");
            }
            return Result.success(user);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        try {
            userService.save(user);
            return Result.success(user);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            userService.updateById(user);
            return Result.success(user);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Long id) {
        try {
            userService.removeById(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/username/{username}")
    public Result<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getByUsername(username);
            if (user == null) {
                return Result.error("User not found");
            }
            return Result.success(user);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}