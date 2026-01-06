package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.Sprint;
import com.se.okr_agile.service.SprintService;
import com.se.okr_agile.vo.CreateSprintRequestVO;
import com.se.okr_agile.vo.UpdateSprintRequestVO;
import com.se.okr_agile.vo.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SprintController {
    @Autowired
    private SprintService sprintService;

    @PostMapping("/sprints")
    public Result createSprint(@RequestBody CreateSprintRequestVO createSprintRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            sprintService.createSprint(createSprintRequestVO);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/sprints")
    public Result<List<Sprint>> getSprints(@RequestParam(required = false) Long teamId) {
        try {
            List<Sprint> sprintList;
            if (teamId != null) {
                sprintList = sprintService.getSprintByTeamId(teamId);
            } else {
                sprintList = sprintService.list();
            }
            return Result.success(sprintList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/sprints/{id}")
    public Result updateSprint(@PathVariable Long id, @RequestBody UpdateSprintRequestVO updateSprintRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            sprintService.updateSprintById(id, updateSprintRequestVO);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/sprints/{id}/close")
    public Result closeSprint(@PathVariable Long id) {
        try {
            sprintService.closeSprint(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/sprints/{id}")
    public Result<Sprint> getSprintById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Sprint sprint = sprintService.getById(id);
            if (sprint == null) {
                return Result.error("Sprint not found");
            }
            return Result.success(sprint);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/sprints/{id}")
    public Result deleteSprint(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            sprintService.removeById(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
