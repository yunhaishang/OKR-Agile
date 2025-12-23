package com.se.okr_agile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.okr_agile.entity.User;
import com.se.okr_agile.vo.UserInfoVO;

public interface UserService extends IService<User> {
    public UserInfoVO getUserInfo(Long id);
}
