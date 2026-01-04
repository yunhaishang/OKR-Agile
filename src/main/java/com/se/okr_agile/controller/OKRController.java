package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.KeyResult;
import com.se.okr_agile.entity.Objective;
import com.se.okr_agile.service.KeyResultService;
import com.se.okr_agile.service.ObjectiveService;
import com.se.okr_agile.service.TaskKrService;
import com.se.okr_agile.service.TaskService;
import com.se.okr_agile.vo.CreateObjectiveRequestVO;
import com.se.okr_agile.vo.OKRVO;
import com.se.okr_agile.vo.UpdateObjectiveRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/okrs")
public class OKRController {

    @Autowired
    private ObjectiveService objectiveService;

    @Autowired
    private KeyResultService keyResultService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskKrService taskKrService;

    @GetMapping
    public Result<List<OKRVO>> getOKRs(@RequestParam(required = false) Long teamId, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<Objective> objectives;

            if (teamId != null) {
                // 根据团队ID查询OKR
                objectives = objectiveService.listByTeamId(teamId);
            } else {
                // 查询所有OKR
                objectives = objectiveService.list();
            }

            List<OKRVO> okrVOList = objectives.stream().map(this::convertToOKRVO).collect(Collectors.toList());

            return Result.success(okrVOList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<OKRVO> getOKRById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Objective objective = objectiveService.getById(id);
            if (objective == null) {
                return Result.error("Objective not found");
            }

            OKRVO okrVO = convertToOKRVO(objective);

            return Result.success(okrVO);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result<OKRVO> createOKR(@RequestBody CreateObjectiveRequestVO createObjectiveRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            Objective objective = new Objective();
            BeanUtils.copyProperties(createObjectiveRequestVO, objective);
            objectiveService.save(objective);

            OKRVO okrVO = convertToOKRVO(objective);

            return Result.success(okrVO);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<OKRVO> updateOKR(@PathVariable Long id, @RequestBody UpdateObjectiveRequestVO updateObjectiveRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            Objective objective = objectiveService.getById(id);
            if (objective == null) {
                return Result.error("Objective not found");
            }

            BeanUtils.copyProperties(updateObjectiveRequestVO, objective);
            objectiveService.updateById(objective);

            OKRVO okrVO = convertToOKRVO(objective);

            return Result.success(okrVO);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteOKR(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            objectiveService.removeById(id);

            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    private OKRVO convertToOKRVO(Objective objective) {
        OKRVO okrVO = new OKRVO();
        BeanUtils.copyProperties(objective, okrVO);

        // 获取关联的Key Results
        List<KeyResult> keyResults = keyResultService.getByObjectiveId(objective.getId());
        okrVO.setKrs(keyResults);

        // 计算任务统计信息
        Integer taskCount = 0;
        Integer completedTaskCount = 0;
        
        for (KeyResult kr : keyResults) {
            // 获取与KR关联的任务数量和完成情况
            Map<String, Object> taskStats = taskService.getTaskStatsByKrId(kr.getId());
            if (taskStats != null) {
                taskCount += (Integer) taskStats.get("total");
                completedTaskCount += (Integer) taskStats.get("completed");
            }
        }
        
        okrVO.setTaskCount(taskCount);
        okrVO.setCompletedTaskCount(completedTaskCount);

        return okrVO;
    }
}