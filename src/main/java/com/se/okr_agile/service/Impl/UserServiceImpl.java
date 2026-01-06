package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
        userInfoVO.setCreate_time(user.getCreate_time());
        userInfoVO.setTeams(teamUserMapper.getTeamsByUserId(id));

        return userInfoVO;
    }
    
    @Override
    public java.util.List<com.se.okr_agile.entity.Team> getTeamsByUserId(Long userId) {
        return teamUserMapper.getTeamsByUserId(userId);
    }

    @Override
    public User getByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        return this.getBaseMapper().selectOne(
                new QueryWrapper<User>().lambda()
                        .eq(User::getUsername, username)
        );
    }
}
