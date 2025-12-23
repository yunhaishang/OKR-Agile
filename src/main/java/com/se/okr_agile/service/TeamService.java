package com.se.okr_agile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.okr_agile.entity.Team;
import com.se.okr_agile.vo.CreateTeamRequestVO;

import java.util.List;

public interface TeamService extends IService<Team> {
    void createTeam(Long userId, CreateTeamRequestVO createTeamRequestVO);

    List<Team> getTeams(Long userId);

    void addMember(Long userId, Long teamId, Long memberId);
}
