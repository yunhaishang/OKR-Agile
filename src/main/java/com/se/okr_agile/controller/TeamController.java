package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.Team;
import com.se.okr_agile.service.TeamService;
import com.se.okr_agile.vo.CreateTeamRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/teams")
    public Result createTeam(@RequestBody CreateTeamRequestVO createTeamRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            teamService.createTeam(userId, createTeamRequestVO);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/teams")
    public Result<List<Team>> getTeams(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<Team> teamList = teamService.getTeams(userId);
            return Result.success(teamList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/teams/current")
    public Result<Team> getCurrentTeam(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            // 这里应该获取用户当前所属的团队
            // 由于当前实现可能不完整，先返回用户信息中的团队
            List<Team> userTeams = teamService.getTeams(userId);
            if (userTeams != null && !userTeams.isEmpty()) {
                // 返回用户第一个团队作为当前团队
                return Result.success(userTeams.get(0));
            } else {
                return Result.error("No team found for user");
            }
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/teams/{teamId}")
    public Result<Team> getTeamById(@PathVariable Long teamId, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Team team = teamService.getById(teamId);
            return Result.success(team);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/teams/{teamId}/members")
    public Result addMember(@PathVariable Long teamId, @RequestBody Long memberId, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            teamService.addMember(userId, teamId, memberId);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
