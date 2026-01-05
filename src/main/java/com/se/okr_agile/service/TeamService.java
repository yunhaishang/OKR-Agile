package com.se.okr_agile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.okr_agile.entity.Team;
import com.se.okr_agile.vo.CreateTeamRequestVO;
import com.se.okr_agile.vo.UpdateTeamRequestVO;
import com.se.okr_agile.vo.UserTeamInfo;

import java.util.List;

public interface TeamService extends IService<Team> {
    Team createTeamByUsername(String username, CreateTeamRequestVO createTeamRequestVO);

    List<Team> getTeamsByUsername(String username);
    
    Team updateTeamByUsername(String username, Long teamId, UpdateTeamRequestVO updateTeamRequestVO);
    
    void deleteTeamByUsername(String username, Long teamId);

    void addMemberByUsername(String username, Long teamId, Long memberId);
    
    void removeMemberByUsername(String username, Long teamId, Long memberId);
    
    List<UserTeamInfo> getMembers(Long teamId);

    void addMember(Long userId, Long teamId, Long memberId);
}
