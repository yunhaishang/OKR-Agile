package com.se.okr_agile.interceptor;

import com.se.okr_agile.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 步骤1：获取请求URL
        String requestURL = request.getRequestURL().toString();
        log.info("拦截到请求: {}", requestURL);

        // 步骤2：判断是否为登录请求
        if (requestURL.contains("/login")) {
            log.info("登录请求，直接放行");
            return true;
        }

        // 步骤3：获取请求头中的令牌
        String token = request.getHeader("Authorization");
        log.info("获取到的令牌: {}", token);

        // 步骤4：检查令牌是否存在
        if (token == null || token.isEmpty()) {
            log.warn("令牌为空，用户未登录，响应401状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("请先登录");
            return false;
        }

        // 步骤5：解析和验证令牌
        try {
            // 提取真正的令牌
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = JwtUtil.extractClaims(token);
            log.info("令牌解析成功，用户信息: {}", claims);

            // 步骤6：将用户信息存入请求
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("username", claims.get("username"));

        } catch (Exception e) {
            log.warn("令牌解析失败: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("令牌无效或已过期");
            return false;
        }

        // 步骤7：放行请求
        log.info("令牌校验通过，放行请求");
        return true;
    }
}