package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.Task;
import com.se.okr_agile.mapper.TaskMapper;
import com.se.okr_agile.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Override
    public Map<String, Object> getTaskStatsByKrId(Long krId) {
        Map<String, Object> stats = new HashMap<>();
        
        // 获取与KR关联的任务总数
        int totalTasks = (int) this.count(new QueryWrapper<Task>().eq("kr_id", krId));
        
        // 获取已完成的任务数
        int completedTasks = (int) this.count(new QueryWrapper<Task>()
            .eq("kr_id", krId)
            .eq("status", "completed"));
        
        stats.put("total", totalTasks);
        stats.put("completed", completedTasks);
        
        return stats;
    }
}