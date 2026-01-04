package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.TaskKr;
import com.se.okr_agile.mapper.TaskKrMapper;
import com.se.okr_agile.service.TaskKrService;
import org.springframework.stereotype.Service;

@Service
public class TaskKrServiceImpl extends ServiceImpl<TaskKrMapper, TaskKr> implements TaskKrService {
}