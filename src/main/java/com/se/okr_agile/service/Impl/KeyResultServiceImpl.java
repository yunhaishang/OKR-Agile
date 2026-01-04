package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.KeyResult;
import com.se.okr_agile.mapper.KeyResultMapper;
import com.se.okr_agile.service.KeyResultService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyResultServiceImpl extends ServiceImpl<KeyResultMapper, KeyResult> implements KeyResultService {
    @Override
    public List<KeyResult> getByObjectiveId(Long objectiveId) {
        return this.list(new QueryWrapper<KeyResult>().eq("objective_id", objectiveId));
    }
}