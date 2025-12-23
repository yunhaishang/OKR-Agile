package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.User;
import com.se.okr_agile.mapper.TeamUserMapper;
import com.se.okr_agile.mapper.UserMapper;
import com.se.okr_agile.service.UserService;
import com.se.okr_agile.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private TeamUserMapper teamUserMapper;

    @Override
    @Transactional
    public UserInfoVO getUserInfo(Long id) {
        User user = this.getById(id);

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setRole(user.getRole());
        userInfoVO.setCreated_at(user.getCreated_at());
        userInfoVO.setTeams(teamUserMapper.getTeamsByUserId(id));

        return userInfoVO;
    }
}
