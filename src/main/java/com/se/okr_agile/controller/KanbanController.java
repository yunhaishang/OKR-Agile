package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.Task;
import com.se.okr_agile.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/kanban")
public class KanbanController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/data")
    public Result<Map<String, Object>> getKanbanData(
            @RequestParam(required = false) Long okrId,
            @RequestParam(required = false) Long sprintId,
            @RequestParam(required = false) String search,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            // 构建查询条件
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Task> queryWrapper = 
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            
            if (okrId != null) {
                // 通过OKR ID查找相关的任务，需要通过KR关联
                // 这里简化处理，实际实现中需要根据Objective->KeyResult->Task的关联关系查询
            }
            if (sprintId != null) queryWrapper.eq("sprint_id", sprintId);
            if (search != null) queryWrapper.like("title", search);
            
            List<Task> taskList = taskService.list(queryWrapper);
            
            // 按状态分组任务
            Map<String, List<Task>> tasksByStatus = new HashMap<>();
            tasksByStatus.put("todo", new ArrayList<>());
            tasksByStatus.put("doing", new ArrayList<>());
            tasksByStatus.put("done", new ArrayList<>());
            tasksByStatus.put("blocked", new ArrayList<>());
            
            for (Task task : taskList) {
                String status = task.getStatus() != null ? task.getStatus() : "todo";
                if (tasksByStatus.containsKey(status)) {
                    tasksByStatus.get(status).add(task);
                } else {
                    tasksByStatus.get("todo").add(task); // 默认放入todo
                }
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("tasks", tasksByStatus);
            response.put("totalTasks", taskList.size());
            response.put("filters", Map.of(
                "okrId", okrId,
                "sprintId", sprintId,
                "search", search
            ));
            
            return Result.success(response);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getKanbanStats(
            @RequestParam(required = false) Long okrId,
            @RequestParam(required = false) Long sprintId,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            // 构建查询条件
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Task> queryWrapper = 
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            
            if (okrId != null) {
                // 通过OKR ID查找相关的任务
            }
            if (sprintId != null) queryWrapper.eq("sprint_id", sprintId);
            
            List<Task> taskList = taskService.list(queryWrapper);
            
            // 统计任务状态
            int todoCount = 0, doingCount = 0, doneCount = 0, blockedCount = 0;
            for (Task task : taskList) {
                if ("todo".equals(task.getStatus())) todoCount++;
                else if ("doing".equals(task.getStatus())) doingCount++;
                else if ("done".equals(task.getStatus())) doneCount++;
                else if ("blocked".equals(task.getStatus())) blockedCount++;
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("total", taskList.size());
            response.put("todo", todoCount);
            response.put("doing", doingCount);
            response.put("done", doneCount);
            response.put("blocked", blockedCount);
            response.put("completionRate", taskList.isEmpty() ? 0 : (double) doneCount / taskList.size() * 100);
            
            return Result.success(response);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/tasks/batch-status")
    public Result<Map<String, Object>> batchUpdateTaskStatus(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            List<Long> taskIds = (List<Long>) requestBody.get("taskIds");
            String status = (String) requestBody.get("status");
            
            if (taskIds == null || taskIds.isEmpty() || status == null) {
                return Result.error("Task IDs and status are required");
            }
            
            int updatedCount = 0;
            for (Long taskId : taskIds) {
                Task task = taskService.getById(taskId);
                if (task != null) {
                    task.setStatus(status);
                    taskService.updateById(task);
                    updatedCount++;
                }
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("updatedCount", updatedCount);
            response.put("taskIds", taskIds);
            response.put("newStatus", status);
            
            return Result.success(response);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/tasks/blocked")
    public Result<List<Task>> getBlockedTasks(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            List<Task> blockedTasks = taskService.list(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Task>()
                    .eq("status", "blocked")
            );
            
            return Result.success(blockedTasks);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/tasks/overdue")
    public Result<List<Task>> getOverdueTasks(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            // 这里简化处理，实际实现中需要比较任务的截止日期和当前日期
            List<Task> overdueTasks = taskService.list(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Task>()
                    .lt("created_at", new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L)) // 假设超过7天为逾期
                    .ne("status", "done")
            );
            
            return Result.success(overdueTasks);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}