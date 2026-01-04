package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.MetricSnapshot;
import com.se.okr_agile.service.MetricSnapshotService;
import com.se.okr_agile.vo.CreateMetricSnapshotRequestVO;
import com.se.okr_agile.vo.UpdateMetricSnapshotRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MetricSnapshotController {

    @Autowired
    private MetricSnapshotService metricSnapshotService;

    @PostMapping("/metricsnapshots")
    public Result createMetricSnapshot(@RequestBody CreateMetricSnapshotRequestVO createMetricSnapshotRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            MetricSnapshot metricSnapshot = new MetricSnapshot();
            metricSnapshot.setTeam_id(createMetricSnapshotRequestVO.getTeam_id());
            metricSnapshot.setSprint_id(createMetricSnapshotRequestVO.getSprint_id());
            metricSnapshot.setSnapshot_date(createMetricSnapshotRequestVO.getSnapshot_date());
            metricSnapshot.setOkr_progress(createMetricSnapshotRequestVO.getOkr_progress());
            metricSnapshot.setTotal_tasks(createMetricSnapshotRequestVO.getTotal_tasks());
            metricSnapshot.setCompleted_tasks(createMetricSnapshotRequestVO.getCompleted_tasks());
            metricSnapshot.setBlocked_tasks(createMetricSnapshotRequestVO.getBlocked_tasks());
            metricSnapshot.setVelocity(createMetricSnapshotRequestVO.getVelocity());
            metricSnapshotService.save(metricSnapshot);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/metricsnapshots")
    public Result<List<MetricSnapshot>> getMetricSnapshots(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<MetricSnapshot> metricSnapshotList = metricSnapshotService.list();
            return Result.success(metricSnapshotList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/metricsnapshots/{id}")
    public Result<MetricSnapshot> getMetricSnapshotById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            MetricSnapshot metricSnapshot = metricSnapshotService.getById(id);
            return Result.success(metricSnapshot);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/metricsnapshots/{id}")
    public Result updateMetricSnapshot(@PathVariable Long id, @RequestBody UpdateMetricSnapshotRequestVO updateMetricSnapshotRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            MetricSnapshot metricSnapshot = new MetricSnapshot();
            metricSnapshot.setId(id);
            metricSnapshot.setTeam_id(updateMetricSnapshotRequestVO.getTeam_id());
            metricSnapshot.setSprint_id(updateMetricSnapshotRequestVO.getSprint_id());
            metricSnapshot.setSnapshot_date(updateMetricSnapshotRequestVO.getSnapshot_date());
            metricSnapshot.setOkr_progress(updateMetricSnapshotRequestVO.getOkr_progress());
            metricSnapshot.setTotal_tasks(updateMetricSnapshotRequestVO.getTotal_tasks());
            metricSnapshot.setCompleted_tasks(updateMetricSnapshotRequestVO.getCompleted_tasks());
            metricSnapshot.setBlocked_tasks(updateMetricSnapshotRequestVO.getBlocked_tasks());
            metricSnapshot.setVelocity(updateMetricSnapshotRequestVO.getVelocity());
            metricSnapshotService.updateById(metricSnapshot);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/metricsnapshots/{id}")
    public Result deleteMetricSnapshot(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            metricSnapshotService.removeById(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}