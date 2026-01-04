package com.se.okr_agile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.okr_agile.entity.Objective;

import java.util.List;

public interface ObjectiveService extends IService<Objective> {
    List<Objective> listByTeamId(Long teamId);
}