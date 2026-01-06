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
import java.util.Map;

@RestController
@RequestMapping("/retrospectives")
public class RetrospectiveReportController {

    @Autowired
    private RetrospectiveReportService retrospectiveReportService;

    @GetMapping
    public Result<List<RetrospectiveReport>> getRetrospectives(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<RetrospectiveReport> retrospectiveReportList = retrospectiveReportService.list();
            return Result.success(retrospectiveReportList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<RetrospectiveReport> getRetrospectiveById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            RetrospectiveReport retrospectiveReport = retrospectiveReportService.getById(id);
            return Result.success(retrospectiveReport);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/generate/{teamId}")
    public Result<RetrospectiveReport> generateRetrospective(@PathVariable Long teamId, @RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            
            // 从requestBody获取周期参数
            String cycleStart = (String) requestBody.get("cycleStart");
            String cycleEnd = (String) requestBody.get("cycleEnd");
            
            // 这里应该调用服务层方法生成报告
            // 简化实现，创建一个模拟的报告
            RetrospectiveReport report = new RetrospectiveReport();
            report.setTeam_id(teamId);
            report.setTitle("Sprint Retrospective Report - " + System.currentTimeMillis());
            report.setHighlights("团队在本周期内完成了大部分目标，协作效率有所提升");
            report.setBottlenecks("部分任务延期，需求变更频繁导致开发效率降低");
            report.setSuggestions("加强需求管理，优化任务分配机制");
            report.setMetrics_json("{'completedTasks': 15, 'totalTasks': 18, 'completionRate': 83.3}");
            retrospectiveReportService.save(report);
            
            return Result.success(report);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    

    
    // 保留原有的CRUD方法，但使用新的路径
    @PostMapping
    public Result createRetrospective(@RequestBody CreateRetrospectiveReportRequestVO createRetrospectiveReportRequestVO, HttpServletRequest request) {
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

    @PutMapping("/{id}")
    public Result updateRetrospective(@PathVariable Long id, @RequestBody UpdateRetrospectiveReportRequestVO updateRetrospectiveReportRequestVO, HttpServletRequest request) {
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

    @DeleteMapping("/{id}")
    public Result deleteRetrospective(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            retrospectiveReportService.removeById(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}