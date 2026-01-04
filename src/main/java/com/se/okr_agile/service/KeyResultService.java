package com.se.okr_agile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.okr_agile.entity.KeyResult;

import java.util.List;

public interface KeyResultService extends IService<KeyResult> {
    List<KeyResult> getByObjectiveId(Long objectiveId);
}