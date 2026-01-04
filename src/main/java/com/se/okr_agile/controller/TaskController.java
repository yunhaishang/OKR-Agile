package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.Task;
import com.se.okr_agile.service.TaskService;
import com.se.okr_agile.vo.CreateTaskRequestVO;
import com.se.okr_agile.vo.UpdateTaskRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result<List<Task>> getTasks(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<Task> taskList = taskService.list();
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
}