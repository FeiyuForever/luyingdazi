package com.luyingdazi.api.interceptor;

import com.luyingdazi.common.constant.AppConstant;
import com.luyingdazi.common.constant.RedisKeyConstant;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.common.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录认证拦截器
 * 从请求头中取 Token，校验后将 userId 放入 UserContext
 *
 * @author luyingdazi
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // OPTIONS 请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader(AppConstant.TOKEN_HEADER);
        if (token == null || token.isBlank()) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }

        // 去掉 Bearer 前缀
        if (token.startsWith(AppConstant.TOKEN_PREFIX)) {
            token = token.substring(AppConstant.TOKEN_PREFIX.length());
        }

        // 从 Redis 中查 Token 对应的 userId
        String userId = redisTemplate.opsForValue().get(RedisKeyConstant.USER_TOKEN + token);
        if (userId == null) {
            throw new BizException(ResultCode.TOKEN_EXPIRED);
        }

        // 设置用户上下文
        UserContext.setUserId(Long.valueOf(userId));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        UserContext.clear();
    }
}
