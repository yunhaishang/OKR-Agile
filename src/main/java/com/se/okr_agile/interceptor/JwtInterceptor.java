package com.se.okr_agile.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.okr_agile.common.Result;
import com.se.okr_agile.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURL = request.getRequestURL().toString();
        log.info("拦截到请求: {}", requestURL);

        String token = request.getHeader("Authorization");

        // 1. 检查令牌是否存在
        if (token == null || token.isEmpty()) {
            log.warn("令牌为空，用户未登录");
            returnJsonResponse(response, Result.error(401, "请先登录"));
            return false;
        }

        try {
            // 2. 提取真正的令牌
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 验证token格式
            if (token.split("\\.").length != 3) {
                log.warn("令牌格式不正确");
                returnJsonResponse(response, Result.error(401, "令牌格式不正确"));
                return false;
            }

            // 4. 解析和验证令牌
            Claims claims = jwtUtil.extractClaims(token);
            log.info("令牌解析成功，用户ID: {}", claims.get("userId"));

            // 5. 将用户信息存入请求
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("username", claims.get("username"));

        } catch (ExpiredJwtException e) {
            log.warn("令牌已过期: {}", e.getMessage());
            returnJsonResponse(response, Result.error(401, "令牌已过期"));
            return false;
        } catch (SignatureException e) {
            log.warn("令牌签名无效: {}", e.getMessage());
            returnJsonResponse(response, Result.error(401, "令牌无效"));
            return false;
        } catch (MalformedJwtException e) {
            log.warn("令牌格式错误: {}", e.getMessage());
            returnJsonResponse(response, Result.error(401, "令牌格式错误"));
            return false;
        } catch (Exception e) {
            log.warn("令牌验证失败: {}", e.getMessage());
            returnJsonResponse(response, Result.error(401, "令牌验证失败: " + e.getMessage()));
            return false;
        }

        log.info("令牌校验通过，放行请求");
        return true;
    }

    /**
     * 返回JSON格式的错误响应
     */
    private void returnJsonResponse(HttpServletResponse response, Result<?> result) {
        try {
            // 设置HTTP状态码为401，而不是业务状态码
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            // 添加CORS头部，确保跨域请求能正确接收响应
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);

            // 获取响应写入器并写入JSON数据
            var writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close(); // 确保响应被完全提交
        } catch (Exception e) {
            log.error("返回JSON响应失败: {}", e.getMessage());
        }
    }
}