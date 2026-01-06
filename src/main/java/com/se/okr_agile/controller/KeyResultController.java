package com.se.okr_agile.controller;

import com.se.okr_agile.common.Result;
import com.se.okr_agile.entity.KeyResult;
import com.se.okr_agile.service.KeyResultService;
import com.se.okr_agile.vo.CreateKeyResultRequestVO;
import com.se.okr_agile.vo.UpdateKeyResultRequestVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KeyResultController {

    @Autowired
    private KeyResultService keyResultService;

    @PostMapping("/keyresults")
    public Result createKeyResult(@RequestBody CreateKeyResultRequestVO createKeyResultRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            KeyResult keyResult = new KeyResult();
            keyResult.setObjective_id(createKeyResultRequestVO.getObjective_id());
            keyResult.setDescription(createKeyResultRequestVO.getDescription());
            keyResult.setMetric_type(createKeyResultRequestVO.getMetric_type());
            keyResult.setTarget_value(createKeyResultRequestVO.getTarget_value());
            keyResult.setCurrent_value(createKeyResultRequestVO.getCurrent_value());
            keyResult.setDisplay_unit(createKeyResultRequestVO.getDisplay_unit());
            keyResult.setWeight(createKeyResultRequestVO.getWeight());
            keyResult.setCreate_user_id(userId);
            keyResultService.save(keyResult);
            return Result.success(keyResult);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/keyresults")
    public Result<List<KeyResult>> getKeyResults(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            List<KeyResult> keyResultList = keyResultService.list();
            return Result.success(keyResultList);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/keyresults/{id}")
    public Result<KeyResult> getKeyResultById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            KeyResult keyResult = keyResultService.getById(id);
            return Result.success(keyResult);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/keyresults/{id}")
    public Result updateKeyResult(@PathVariable Long id, @RequestBody UpdateKeyResultRequestVO updateKeyResultRequestVO, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            KeyResult keyResult = keyResultService.getById(id);
            if (keyResult == null) {
                return Result.error("KeyResult not found");
            }
            keyResult.setDescription(updateKeyResultRequestVO.getDescription());
            keyResult.setMetric_type(updateKeyResultRequestVO.getMetric_type());
            keyResult.setTarget_value(updateKeyResultRequestVO.getTarget_value());
            if (updateKeyResultRequestVO.getCurrent_value() != null) keyResult.setCurrent_value(updateKeyResultRequestVO.getCurrent_value());
            keyResult.setDisplay_unit(updateKeyResultRequestVO.getDisplay_unit());
            keyResult.setWeight(updateKeyResultRequestVO.getWeight());
            keyResultService.updateById(keyResult);
            return Result.success(keyResult);
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/keyresults/{id}")
    public Result deleteKeyResult(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            keyResultService.removeById(id);
            return Result.success();
        } catch(RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}