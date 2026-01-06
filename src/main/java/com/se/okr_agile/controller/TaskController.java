package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.Task;
import com.se.okr_agile.entity.TaskKr;
import com.se.okr_agile.service.KeyResultService;
import com.se.okr_agile.service.ObjectiveService;
import com.se.okr_agile.service.TaskKrService;
import com.se.okr_agile.service.TaskService;
import com.se.okr_agile.vo.CreateTaskRequestVO;
import com.se.okr_agile.vo.UpdateTaskRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskKrService taskKrService;

    @PostMapping("/tasks")
    public Result createTask(@RequestBody CreateTaskRequestVO createTaskRequestVO, HttpServletRequest request) {
        try {
            Long userId = Long.valueOf(request.getAttribute("userId").toString());
            Task task = new Task();
            task.setTeam_id(createTaskRequestVO.getTeam_id());
            task.setSprint_id(createTaskRequestVO.getSprint_id());
            task.setKr_id(createTaskRequestVO.getKr_id());
            task.setTitle(createTaskRequestVO.getTitle());
            task.setDescription(createTaskRequestVO.getDescription());
            task.setStatus(createTaskRequestVO.getStatus());
            task.setPriority(createTaskRequestVO.getPriority());
            task.setType(createTaskRequestVO.getType());
            task.setStory_points(createTaskRequestVO.getStory_points());
            task.setAssignee_id(createTaskRequestVO.getAssignee_id());
            task.setDue_date(createTaskRequestVO.getDue_date());
            task.setEstimated_hours(createTaskRequestVO.getEstimated_hours());
            task.setActual_hours(createTaskRequestVO.getActual_hours());
            task.setCode_contribution_score(createTaskRequestVO.getCode_contribution_score() != null ? java.math.BigDecimal.valueOf(createTaskRequestVO.getCode_contribution_score()) : null);
            task.setCreate_user_id(createTaskRequestVO.getCreate_user_id() != null ? createTaskRequestVO.getCreate_user_id() : userId);
            taskService.save(task);

            // 如果提供了kr_id，创建TaskKr关联
            if (createTaskRequestVO.getKr_id() != null) {
                com.se.okr_agile.entity.TaskKr taskKr = new com.se.okr_agile.entity.TaskKr();
                taskKr.setTask_id(task.getId());
                taskKr.setKr_id(createTaskRequestVO.getKr_id());
                // 使用默认权重1.0
                taskKr.setWeight(java.math.BigDecimal.valueOf(1.0));
                taskKrService.save(taskKr);
            }

            return Result.success(task);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/tasks")
    public Result<List<Task>> getTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long krId,
            @RequestParam(required = false) Long sprintId,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) String search,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            // 构建查询条件
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Task> queryWrapper = 
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            
            if (status != null) queryWrapper.eq("status", status);
            if (krId != null) queryWrapper.eq("kr_id", krId);
            if (sprintId != null) queryWrapper.eq("sprint_id", sprintId);
            if (priority != null) queryWrapper.eq("priority", priority);
            if (type != null) queryWrapper.eq("type", type);
            if (assigneeId != null) queryWrapper.eq("assignee_id", assigneeId);
            if (teamId != null) queryWrapper.eq("team_id", teamId);
            if (search != null) queryWrapper.like("title", search);
            
            List<Task> taskList = taskService.list(queryWrapper);
            return Result.success(taskList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/tasks/{id}")
    public Result<Task> getTaskById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Task task = taskService.getById(id);
            return Result.success(task);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/tasks/{id}")
    public Result updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequestVO updateTaskRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Task task = taskService.getById(id);
            if (task == null) {
                return Result.error("Task not found");
            }
            
            task.setTitle(updateTaskRequestVO.getTitle());
            task.setDescription(updateTaskRequestVO.getDescription());
            task.setStatus(updateTaskRequestVO.getStatus());
            task.setPriority(updateTaskRequestVO.getPriority());
            task.setType(updateTaskRequestVO.getType());
            task.setStory_points(updateTaskRequestVO.getStory_points());
            task.setAssignee_id(updateTaskRequestVO.getAssignee_id());
            if (updateTaskRequestVO.getDue_date() != null) task.setDue_date(updateTaskRequestVO.getDue_date());
            if (updateTaskRequestVO.getEstimated_hours() != null) task.setEstimated_hours(updateTaskRequestVO.getEstimated_hours());
            if (updateTaskRequestVO.getActual_hours() != null) task.setActual_hours(updateTaskRequestVO.getActual_hours());
            if (updateTaskRequestVO.getCode_contribution_score() != null) task.setCode_contribution_score(java.math.BigDecimal.valueOf(updateTaskRequestVO.getCode_contribution_score()));
            if (updateTaskRequestVO.getCreate_user_id() != null) task.setCreate_user_id(updateTaskRequestVO.getCreate_user_id());
            
            taskService.updateById(task);
            return Result.success(task);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PatchMapping("/tasks/{id}/status")
    public Result<Task> updateTaskStatus(@PathVariable Long id, @RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            String status = (String) requestBody.get("status");
            
            if (status == null) {
                return Result.error("Status is required");
            }
            
            Task task = taskService.getById(id);
            if (task == null) {
                return Result.error("Task not found");
            }
            
            // 如果状态变为完成且actual_hours未设置，则设置为estimated_hours
            if ("done".equals(status) && task.getActual_hours() == null && task.getEstimated_hours() != null) {
                task.setActual_hours(task.getEstimated_hours());
            }
            
            task.setStatus(status);
            taskService.updateById(task);
            
            // 更新关联的KR和Objective进度
            updateObjectiveProgress(task.getKr_id());
            
            return Result.success(task);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/tasks/{id}")
    public Result deleteTask(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            taskService.removeById(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    @Autowired
    private KeyResultService keyResultService;
    
    @Autowired
    private ObjectiveService objectiveService;
    
    private void updateObjectiveProgress(Long krId) {
        if (krId == null) return;
        
        // 获取KR信息
        com.se.okr_agile.entity.KeyResult kr = keyResultService.getById(krId);
        if (kr == null) return;
        
        // 获取与KR关联的Objective
        Long objectiveId = kr.getObjective_id();
        if (objectiveId == null) return;
        
        com.se.okr_agile.entity.Objective objective = objectiveService.getById(objectiveId);
        if (objective == null) return;
        
        // 计算Objective的进度（基于关联任务的完成情况）
        // 这里简化处理，实际实现中需要根据业务逻辑计算
        java.util.List<com.se.okr_agile.entity.Task> tasks = taskService.list(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.se.okr_agile.entity.Task>()
                .eq("kr_id", krId)
        );
        
        if (!tasks.isEmpty()) {
            long completedTasks = tasks.stream().filter(task -> "done".equals(task.getStatus())).count();
            java.math.BigDecimal progress = new java.math.BigDecimal(completedTasks * 100.0 / tasks.size()).setScale(2, java.math.RoundingMode.HALF_UP);
            
            objective.setProgress(progress);
            objectiveService.updateById(objective);
        }
    }
}
