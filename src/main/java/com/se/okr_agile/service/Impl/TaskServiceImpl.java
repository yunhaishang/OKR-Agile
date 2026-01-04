package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.Task;
import com.se.okr_agile.mapper.TaskMapper;
import com.se.okr_agile.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
}