package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.Objective;
import com.se.okr_agile.mapper.ObjectiveMapper;
import com.se.okr_agile.service.ObjectiveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectiveServiceImpl extends ServiceImpl<ObjectiveMapper, Objective> implements ObjectiveService {
    @Override
    public List<Objective> listByTeamId(Long teamId) {
        return this.list(new QueryWrapper<Objective>().eq("team_id", teamId));
    }
}