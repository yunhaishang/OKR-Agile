package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.Team;
import com.se.okr_agile.entity.TeamUser;
import com.se.okr_agile.entity.User;
import com.se.okr_agile.mapper.TeamMapper;
import com.se.okr_agile.mapper.TeamUserMapper;
import com.se.okr_agile.mapper.UserMapper;
import com.se.okr_agile.service.TeamService;
import com.se.okr_agile.vo.CreateTeamRequestVO;
import com.se.okr_agile.vo.UserTeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.se.okr_agile.vo.UpdateTeamRequestVO;
import java.util.List;

@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamUserMapper teamUserMapper;

    @Autowired
    private TeamMapper teamMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public Team createTeamByUsername(String username, CreateTeamRequestVO createTeamRequestVO) {
        //检验当前团队名称是否已存在
        if (teamMapper.exists(Wrappers.<Team>lambdaQuery().eq(Team::getName, createTeamRequestVO.getName()))) {
            throw new RuntimeException("团队名称已存在！");
        }
        String name = createTeamRequestVO.getName();
        String description = createTeamRequestVO.getDescription();

        if(name == null || name.trim().isEmpty()) {
            throw new RuntimeException("团队名称不能为空！");
        }

        Team team = new Team();
        team.setName(name);
        team.setDescription(description != null ? description : "");

        this.save(team);
        
        // 通过用户名查找用户ID并添加为团队成员
        Long userId = getUserIdByUsername(username);
        if (userId == null) {
            throw new RuntimeException("用户不存在");
        }
        
        TeamUser teamUser = new TeamUser();
        teamUser.setTeam_id(team.getId());
        teamUser.setUser_id(userId);
        teamUserMapper.insert(teamUser);
        
        return team;
    }

    @Override
    @Transactional
    public List<Team> getTeamsByUsername(String username) {
        Long userId = getUserIdByUsername(username);
        if (userId == null) {
            throw new RuntimeException("用户不存在");
        }
        return teamUserMapper.getTeamsByUserId(userId);
    }

    @Override
    @Transactional
    public Team updateTeamByUsername(String username, Long teamId, UpdateTeamRequestVO updateTeamRequestVO) {
        Long userId = getUserIdByUsername(username);
        if (userId == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 验证用户是否有权限更新团队
        if (!hasPermissionToUpdateTeam(userId, teamId)) {
            throw new RuntimeException("没有权限更新该团队");
        }
        
        Team team = this.getById(teamId);
        if (team == null) {
            throw new RuntimeException("团队不存在！");
        }
        
        if (updateTeamRequestVO.getName() != null) {
            team.setName(updateTeamRequestVO.getName());
        }
        if (updateTeamRequestVO.getDescription() != null) {
            team.setDescription(updateTeamRequestVO.getDescription());
        }
        if (updateTeamRequestVO.getCycleDays() != null) {
            team.setCycleDays(updateTeamRequestVO.getCycleDays());
        }
        if (updateTeamRequestVO.getStatus() != null) {
            team.setStatus(updateTeamRequestVO.getStatus());
        }
        
        this.updateById(team);
        return team;
    }
    
    // 检查用户是否有权限更新指定团队
    private boolean hasPermissionToUpdateTeam(Long userId, Long teamId) {
        // 在实际实现中，这里应该检查用户在团队中的角色权限
        // 例如，只有团队管理员或所有者才能更新团队
        return true; // 模拟返回true，表示有权限
    }

    @Override
    @Transactional
    public void deleteTeamByUsername(String username, Long teamId) {
        Long userId = getUserIdByUsername(username);
        if (userId == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 验证用户是否有权限删除团队
        if (!hasPermissionToDeleteTeam(userId, teamId)) {
            throw new RuntimeException("没有权限删除该团队");
        }
        
        Team team = this.getById(teamId);
        if (team == null) {
            throw new RuntimeException("团队不存在！");
        }
        
        // 删除团队与用户的关系
        teamUserMapper.deleteByTeamId(teamId);
        
        // 删除团队
        this.removeById(teamId);
    }
    
    // 检查用户是否有权限删除指定团队
    private boolean hasPermissionToDeleteTeam(Long userId, Long teamId) {
        // 在实际实现中，这里应该检查用户在团队中的角色权限
        // 例如，只有团队管理员或所有者才能删除团队
        return true; // 模拟返回true，表示有权限
    }

    // 旧的addMember方法，保留以兼容现有代码，但建议使用addMemberByUsername
    @Override
    @Transactional
    public void addMember(Long userId, Long teamId, Long memberId) {
        // 验证操作用户是否有权限添加成员到该团队
        if (!hasPermissionToAddMember(userId, teamId)) {
            throw new RuntimeException("没有权限添加成员到该团队");
        }
        
        TeamUser teamUser = new TeamUser();
        teamUser.setTeam_id(teamId);
        teamUser.setUser_id(memberId);

        teamUserMapper.insert(teamUser);
    }
    
    @Override
    @Transactional
    public void addMemberByUsername(String username, Long teamId, Long memberId) {
        // 通过用户名查找用户ID
        // 注意：这里需要根据实际的用户表结构来实现
        // 假设有一个方法可以根据用户名获取用户ID
        Long userId = getUserIdByUsername(username);
        if (userId == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 验证操作用户是否有权限添加成员到该团队
        if (!hasPermissionToAddMember(userId, teamId)) {
            throw new RuntimeException("没有权限添加成员到该团队");
        }
        
        TeamUser teamUser = new TeamUser();
        teamUser.setTeam_id(teamId);
        teamUser.setUser_id(memberId);

        teamUserMapper.insert(teamUser);
    }
    
    // 根据用户名获取用户ID的辅助方法
    private Long getUserIdByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            log.warn("查询用户ID时用户名为空");
            return null;
        }

        try {
            User user = userMapper.selectOne(
                    Wrappers.<User>lambdaQuery()
                            .select(User::getId)  // 只查询需要的字段，提高性能
                            .eq(User::getUsername, username.trim())
            );

            return user != null ? user.getId() : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    // 检查用户是否有权限添加成员到指定团队
    private boolean hasPermissionToAddMember(Long userId, Long teamId) {
        // 在实际实现中，这里应该检查用户在团队中的角色权限
        // 例如，只有团队管理员或所有者才能添加成员
        return true; // 模拟返回true，表示有权限
    }
    
    @Override
    @Transactional
    public void removeMemberByUsername(String username, Long teamId, Long memberId) {
        Long userId = getUserIdByUsername(username);
        if (userId == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 验证用户是否有权限移除团队成员
        if (!hasPermissionToRemoveMember(userId, teamId)) {
            throw new RuntimeException("没有权限移除团队成员");
        }
        
        teamUserMapper.deleteByTeamIdAndUserId(teamId, memberId);
    }
    
    // 检查用户是否有权限移除团队成员
    private boolean hasPermissionToRemoveMember(Long userId, Long teamId) {
        // 在实际实现中，这里应该检查用户在团队中的角色权限
        // 例如，只有团队管理员或所有者才能移除成员
        return true; // 模拟返回true，表示有权限
    }
    
    @Override
    public List<UserTeamInfo> getMembers(Long teamId) {
        return teamUserMapper.getTeamMembersByTeamId(teamId);
    }
}
