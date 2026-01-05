package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.Team;
import com.se.okr_agile.service.TeamService;
import com.se.okr_agile.vo.CreateTeamRequestVO;
import com.se.okr_agile.vo.UpdateTeamRequestVO;
import com.se.okr_agile.vo.UserTeamInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/teams")
    public Result createTeam(@RequestBody CreateTeamRequestVO createTeamRequestVO, HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            teamService.createTeamByUsername(username, createTeamRequestVO);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/teams")
    public Result<List<Team>> getTeams(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            List<Team> teamList = teamService.getTeamsByUsername(username);
            return Result.success(teamList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/teams/current")
    public Result<Team> getCurrentTeam(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            // 这里应该获取用户当前所属的团队
            // 由于当前实现可能不完整，先返回用户信息中的团队
            List<Team> userTeams = teamService.getTeamsByUsername(username);
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
            // 这里可以验证用户是否有权限查看该团队
            Team team = teamService.getById(teamId);
            return Result.success(team);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/teams/{teamId}/members")
    public Result addMember(@PathVariable Long teamId, @RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            Object memberIdObj = requestBody.get("memberId");
            if (memberIdObj == null) {
                return Result.error("Member ID is required");
            }
            Long memberId = Long.valueOf(memberIdObj.toString());
            teamService.addMemberByUsername(username, teamId, memberId);
            return Result.success();
        } catch(NumberFormatException e) {
            return Result.error("Invalid member ID format");
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/teams/{teamId}")
    public Result deleteTeam(@PathVariable Long teamId, HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            teamService.deleteTeamByUsername(username, teamId);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/teams/{teamId}")
    public Result updateTeam(@PathVariable Long teamId, @RequestBody UpdateTeamRequestVO updateTeamRequestVO, HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            teamService.updateTeamByUsername(username, teamId, updateTeamRequestVO);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/teams/{teamId}/members/{memberId}")
    public Result removeMember(@PathVariable Long teamId, @PathVariable Long memberId, HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            teamService.removeMemberByUsername(username, teamId, memberId);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/teams/{teamId}/members")
    public Result<List<UserTeamInfo>> getMembers(@PathVariable Long teamId, HttpServletRequest request) {
        try {
            // 这里可以验证用户是否有权限查看团队成员
            return Result.success(teamService.getMembers(teamId));
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    //获得团队成员人数
    @GetMapping("/teams/{teamId}/memberCount")
    public Result<Integer> getMemberCount(@PathVariable Long teamId, HttpServletRequest request) {
        try {
            // 这里可以验证用户是否有权限查看团队成员
            return Result.success(teamService.getMembers(teamId).size());
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
