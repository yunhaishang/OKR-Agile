package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.Sprint;
import com.se.okr_agile.mapper.SprintMapper;
import com.se.okr_agile.service.SprintService;
import com.se.okr_agile.vo.CreateSprintRequestVO;
import com.se.okr_agile.vo.UpdateSprintRequestVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SprintServiceImpl extends ServiceImpl<SprintMapper, Sprint> implements SprintService {
    @Override
    @Transactional
    public void createSprint(CreateSprintRequestVO createSprintRequestVO) {
        Long team_id = createSprintRequestVO.getTeam_id();
        String name = createSprintRequestVO.getName();
        LocalDateTime start_date = createSprintRequestVO.getStart_date();
        LocalDateTime end_date = createSprintRequestVO.getEnd_date();

        Sprint sprint = new Sprint();
        sprint.setTeam_id(team_id);
        sprint.setName(name);
        sprint.setStart_date(start_date);
        sprint.setEnd_date(end_date);

        this.save(sprint);
    }

    @Override
    @Transactional
    public List<Sprint> getSprintByTeamId(Long teamId) {
        return  this.lambdaQuery()
                .eq(Sprint::getTeam_id, teamId)
                .list();
    }

    @Override
    @Transactional
    public void updateSprintById(Long sprintId, UpdateSprintRequestVO updateSprintRequestVO) {
        String name = updateSprintRequestVO.getName();
        LocalDateTime start_date = updateSprintRequestVO.getStart_date();
        LocalDateTime end_date = updateSprintRequestVO.getEnd_date();

        Sprint sprint = new Sprint();
        sprint.setId(sprintId);
        sprint.setName(name);
        sprint.setStart_date(start_date);
        sprint.setEnd_date(end_date);

        this.updateById(sprint);
    }

    @Override
    @Transactional
    public void closeSprint(Long id) {
        Sprint sprint = this.getById(id);
        sprint.setStatus("close");

        this.updateById(sprint);
    }
}
