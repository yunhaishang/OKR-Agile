package com.se.okr_agile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.okr_agile.entity.Task;

import java.util.Map;

public interface TaskService extends IService<Task> {
    Map<String, Object> getTaskStatsByKrId(Long krId);
}