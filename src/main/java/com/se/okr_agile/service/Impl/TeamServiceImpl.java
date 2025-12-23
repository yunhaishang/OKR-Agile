package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.Team;
import com.se.okr_agile.entity.TeamUser;
import com.se.okr_agile.mapper.TeamMapper;
import com.se.okr_agile.mapper.TeamUserMapper;
import com.se.okr_agile.service.TeamService;
import com.se.okr_agile.vo.CreateTeamRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamUserMapper teamUserMapper;

    @Override
    @Transactional
    public void createTeam(Long userId, CreateTeamRequestVO createTeamRequestVO) {
        String name = createTeamRequestVO.getName();
        String description = createTeamRequestVO.getDescription();

        if(name == null) {
            throw new RuntimeException("团队无名称！");
        }

        if(description == null) {
            throw new RuntimeException("团队无描述！");
        }

        Team team = new Team();
        team.setName(name);
        team.setDescription(description);

        this.save(team);
    }

    @Override
    @Transactional
    public List<Team> getTeams(Long userId) {
        return teamUserMapper.getTeamsByUserId(userId);
    }

    @Override
    @Transactional
    public void addMember(Long userId, Long teamId, Long memberId) {
        TeamUser teamUser = new TeamUser();
        teamUser.setTeam_id(teamId);
        teamUser.setUser_id(memberId);

        teamUserMapper.insert(teamUser);
    }
}
