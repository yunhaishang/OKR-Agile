package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.Objective;
import com.se.okr_agile.service.ObjectiveService;
import com.se.okr_agile.vo.CreateObjectiveRequestVO;
import com.se.okr_agile.vo.UpdateObjectiveRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ObjectiveController {

    @Autowired
    private ObjectiveService objectiveService;

    @PostMapping("/objectives")
    public Result createObjective(@RequestBody CreateObjectiveRequestVO createObjectiveRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Objective objective = new Objective();
            objective.setTeam_id(createObjectiveRequestVO.getTeam_id());
            objective.setSprint_id(createObjectiveRequestVO.getSprint_id());
            objective.setTitle(createObjectiveRequestVO.getTitle());
            objective.setDescription(createObjectiveRequestVO.getDescription());
            objectiveService.save(objective);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/objectives")
    public Result<List<Objective>> getObjectives(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<Objective> objectiveList = objectiveService.list();
            return Result.success(objectiveList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/objectives/{id}")
    public Result<Objective> getObjectiveById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Objective objective = objectiveService.getById(id);
            return Result.success(objective);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/objectives/{id}")
    public Result updateObjective(@PathVariable Long id, @RequestBody UpdateObjectiveRequestVO updateObjectiveRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Objective objective = new Objective();
            objective.setId(id);
            objective.setTitle(updateObjectiveRequestVO.getTitle());
            objective.setDescription(updateObjectiveRequestVO.getDescription());
            objectiveService.updateById(objective);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/objectives/{id}")
    public Result deleteObjective(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            objectiveService.removeById(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}