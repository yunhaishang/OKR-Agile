package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiAnalysisController {

    @PostMapping("/retrospective/{teamId}")
    public Result<Map<String, Object>> detectAnomalies(@PathVariable Long teamId, @RequestBody Map<String, Object> request, HttpServletRequest httpServletRequest) {
        try {
            Long userId = (Long) httpServletRequest.getAttribute("userId");
            
            // 模拟检测异常
            Map<String, Object> response = new HashMap<>();
            response.put("teamId", teamId);
            response.put("anomalies", new String[]{"低完成率", "进度延迟"});
            response.put("confidence", 0.85);
            response.put("timestamp", System.currentTimeMillis());
            
            return Result.success(response);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/root-cause/{teamId}")
    public Result<Map<String, Object>> analyzeRootCause(@PathVariable Long teamId, @RequestBody Map<String, Object> request, HttpServletRequest httpServletRequest) {
        try {
            Long userId = (Long) httpServletRequest.getAttribute("userId");
            
            // 模拟分析根因
            Map<String, Object> response = new HashMap<>();
            response.put("teamId", teamId);
            response.put("rootCauses", new String[]{"资源不足", "需求变更频繁"});
            response.put("suggestions", new String[]{"增加人手", "明确需求"});
            response.put("confidence", 0.92);
            
            return Result.success(response);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/prediction/{teamId}")
    public Result<Map<String, Object>> executePrediction(@PathVariable Long teamId, @RequestBody Map<String, Object> request, HttpServletRequest httpServletRequest) {
        try {
            Long userId = (Long) httpServletRequest.getAttribute("userId");
            
            // 模拟执行预测
            Map<String, Object> response = new HashMap<>();
            response.put("teamId", teamId);
            response.put("predictions", new String[]{"下周期完成率85%", "风险等级中等"});
            response.put("confidence", 0.78);
            response.put("nextSprintForecast", "预计完成80%的目标");
            
            return Result.success(response);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/status/{teamId}")
    public Result<Map<String, Object>> getAiStatus(@PathVariable Long teamId, HttpServletRequest httpServletRequest) {
        try {
            Long userId = (Long) httpServletRequest.getAttribute("userId");
            
            // 获取AI状态
            Map<String, Object> response = new HashMap<>();
            response.put("teamId", teamId);
            response.put("status", "active");
            response.put("lastAnalysis", System.currentTimeMillis());
            response.put("analysisCount", 42);
            response.put("modelVersion", "1.0.0");
            
            return Result.success(response);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/statistics")
    public Result<Map<String, Object>> getAiStatistics(HttpServletRequest httpServletRequest) {
        try {
            Long userId = (Long) httpServletRequest.getAttribute("userId");
            
            // 获取AI统计信息
            Map<String, Object> response = new HashMap<>();
            response.put("totalAnalysis", 156);
            response.put("accuracyRate", 0.89);
            response.put("activeModels", 5);
            response.put("lastUpdated", System.currentTimeMillis());
            response.put("successRate", 0.94);
            
            return Result.success(response);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}