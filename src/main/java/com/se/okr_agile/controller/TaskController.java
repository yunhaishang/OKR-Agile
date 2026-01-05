package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.Task;
import com.se.okr_agile.service.KeyResultService;
import com.se.okr_agile.service.ObjectiveService;
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

    @PostMapping("/tasks")
    public Result createTask(@RequestBody CreateTaskRequestVO createTaskRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
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
            taskService.save(task);
            return Result.success();
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
            Task task = new Task();
            task.setId(id);
            task.setTitle(updateTaskRequestVO.getTitle());
            task.setDescription(updateTaskRequestVO.getDescription());
            task.setStatus(updateTaskRequestVO.getStatus());
            task.setPriority(updateTaskRequestVO.getPriority());
            task.setType(updateTaskRequestVO.getType());
            task.setStory_points(updateTaskRequestVO.getStory_points());
            task.setAssignee_id(updateTaskRequestVO.getAssignee_id());
            taskService.updateById(task);
            return Result.success();
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