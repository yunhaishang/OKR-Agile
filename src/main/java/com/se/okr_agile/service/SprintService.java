package com.se.okr_agile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.okr_agile.entity.Sprint;
import com.se.okr_agile.vo.CreateSprintRequestVO;
import com.se.okr_agile.vo.UpdateSprintRequestVO;

import java.util.List;

public interface SprintService extends IService<Sprint> {
    void createSprint(CreateSprintRequestVO createSprintRequestVO);

    List<Sprint> getSprintByTeamId(Long teamId);

    void updateSprintById(Long sprintId, UpdateSprintRequestVO updateSprintRequestVO);

    void closeSprint(Long id);
}
