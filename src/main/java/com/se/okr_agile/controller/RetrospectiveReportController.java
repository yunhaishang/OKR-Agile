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

    @PostMapping("/generate")
    public Result<RetrospectiveReport> generateRetrospective(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            // 从requestBody获取period参数
            String period = (String) requestBody.get("period");
            
            // 这里应该调用服务层方法生成报告
            // 由于没有现成的服务方法，这里返回一个错误信息
            return Result.error("Generate report method not implemented yet");
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