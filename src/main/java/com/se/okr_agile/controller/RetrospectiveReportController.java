package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.RetrospectiveReport;
import com.se.okr_agile.service.RetrospectiveReportService;
import com.se.okr_agile.vo.CreateRetrospectiveReportRequestVO;
import com.se.okr_agile.vo.UpdateRetrospectiveReportRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RetrospectiveReportController {

    @Autowired
    private RetrospectiveReportService retrospectiveReportService;

    @PostMapping("/retrospectivereports")
    public Result createRetrospectiveReport(@RequestBody CreateRetrospectiveReportRequestVO createRetrospectiveReportRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            RetrospectiveReport retrospectiveReport = new RetrospectiveReport();
            retrospectiveReport.setTeam_id(createRetrospectiveReportRequestVO.getTeam_id());
            retrospectiveReport.setSprint_id(createRetrospectiveReportRequestVO.getSprint_id());
            retrospectiveReport.setTitle(createRetrospectiveReportRequestVO.getTitle());
            retrospectiveReport.setHighlights(createRetrospectiveReportRequestVO.getHighlights());
            retrospectiveReport.setBottlenecks(createRetrospectiveReportRequestVO.getBottlenecks());
            retrospectiveReport.setSuggestions(createRetrospectiveReportRequestVO.getSuggestions());
            retrospectiveReport.setMetrics_json(createRetrospectiveReportRequestVO.getMetrics_json());
            retrospectiveReportService.save(retrospectiveReport);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/retrospectivereports")
    public Result<List<RetrospectiveReport>> getRetrospectiveReports(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<RetrospectiveReport> retrospectiveReportList = retrospectiveReportService.list();
            return Result.success(retrospectiveReportList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/retrospectivereports/{id}")
    public Result<RetrospectiveReport> getRetrospectiveReportById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            RetrospectiveReport retrospectiveReport = retrospectiveReportService.getById(id);
            return Result.success(retrospectiveReport);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/retrospectivereports/{id}")
    public Result updateRetrospectiveReport(@PathVariable Long id, @RequestBody UpdateRetrospectiveReportRequestVO updateRetrospectiveReportRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            RetrospectiveReport retrospectiveReport = new RetrospectiveReport();
            retrospectiveReport.setId(id);
            retrospectiveReport.setTitle(updateRetrospectiveReportRequestVO.getTitle());
            retrospectiveReport.setHighlights(updateRetrospectiveReportRequestVO.getHighlights());
            retrospectiveReport.setBottlenecks(updateRetrospectiveReportRequestVO.getBottlenecks());
            retrospectiveReport.setSuggestions(updateRetrospectiveReportRequestVO.getSuggestions());
            retrospectiveReport.setMetrics_json(updateRetrospectiveReportRequestVO.getMetrics_json());
            retrospectiveReportService.updateById(retrospectiveReport);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/retrospectivereports/{id}")
    public Result deleteRetrospectiveReport(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            retrospectiveReportService.removeById(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}