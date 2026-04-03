package ClothesShop.spring_restapi_clothesshop.security;

import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RateLimitInterceptor.class);

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.rate-limit.enabled:true}")
    private boolean enabled;

    @Value("${app.rate-limit.max-requests:120}")
    private long maxRequests;

    @Value("${app.rate-limit.window-seconds:60}")
    private long windowSeconds;

    public RateLimitInterceptor(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!enabled || "OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        try {
            String key = buildRateLimitKey(request);
            Long current = redisTemplate.opsForValue().increment(key);

            if (current != null && current == 1) {
                redisTemplate.expire(key, windowSeconds, TimeUnit.SECONDS);
            }

            if (current != null && current > maxRequests) {
                Long ttlSeconds = redisTemplate.getExpire(key, TimeUnit.SECONDS);
                if (ttlSeconds != null && ttlSeconds > 0) {
                    response.setHeader("Retry-After", String.valueOf(ttlSeconds));
                }

                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");

                ApiResponse<Void> body = ApiResponse.ofError(
                        HttpStatus.TOO_MANY_REQUESTS.value(),
                        "Bạn gọi API quá nhanh. Vui lòng thử lại sau.",
                        "Too Many Requests");
                response.getWriter().write(objectMapper.writeValueAsString(body));
                return false;
            }
        } catch (Exception ex) {
            // Fail-open: nếu Redis có sự cố vẫn cho request đi qua để hệ thống không bị tê liệt.
            log.warn("Rate limiting bỏ qua do lỗi Redis: {}", ex.getMessage());
        }

        return true;
    }

    private String buildRateLimitKey(HttpServletRequest request) {
        String ip = extractClientIp(request);
        String userPart = resolveUserPart();
        String method = request.getMethod();
        String path = request.getRequestURI();
        return "rate_limit:%s:%s:%s:%s".formatted(userPart, ip, method, path);
    }

    private String resolveUserPart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        }
        return authentication.getName();
    }

    private String extractClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isBlank()) {
            return xRealIp.trim();
        }
        return request.getRemoteAddr();
    }
}