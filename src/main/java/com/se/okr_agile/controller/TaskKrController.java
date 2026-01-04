package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.TaskKr;
import com.se.okr_agile.service.TaskKrService;
import com.se.okr_agile.vo.CreateTaskKrRequestVO;
import com.se.okr_agile.vo.UpdateTaskKrRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskKrController {

    @Autowired
    private TaskKrService taskKrService;

    @PostMapping("/taskkrs")
    public Result createTaskKr(@RequestBody CreateTaskKrRequestVO createTaskKrRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            TaskKr taskKr = new TaskKr();
            taskKr.setTask_id(createTaskKrRequestVO.getTask_id());
            taskKr.setKr_id(createTaskKrRequestVO.getKr_id());
            taskKr.setWeight(createTaskKrRequestVO.getWeight());
            taskKrService.save(taskKr);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/taskkrs")
    public Result<List<TaskKr>> getTaskKrs(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<TaskKr> taskKrList = taskKrService.list();
            return Result.success(taskKrList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/taskkrs/{taskId}/{krId}")
    public Result<TaskKr> getTaskKrById(@PathVariable Long taskId, @PathVariable Long krId, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            TaskKr result = taskKrService.getOne(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<TaskKr>()
                    .eq("task_id", taskId)
                    .eq("kr_id", krId)
            );
            return Result.success(result);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/taskkrs/{taskId}/{krId}")
    public Result updateTaskKr(@PathVariable Long taskId, @PathVariable Long krId, @RequestBody UpdateTaskKrRequestVO updateTaskKrRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            TaskKr taskKr = new TaskKr();
            taskKr.setWeight(updateTaskKrRequestVO.getWeight());
            taskKrService.update(taskKr, 
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<TaskKr>()
                    .eq("task_id", taskId)
                    .eq("kr_id", krId)
            );
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/taskkrs/{taskId}/{krId}")
    public Result deleteTaskKr(@PathVariable Long taskId, @PathVariable Long krId, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            taskKrService.remove(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<TaskKr>()
                    .eq("task_id", taskId)
                    .eq("kr_id", krId)
            );
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}