package com.mrqinzh.blog.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.blog.model.enums.AppStatus;
import com.mrqinzh.blog.util.RedisUtil;
import com.mrqinzh.blog.model.dto.resp.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mrqinzh
 * @Description 当前拦截器只是对部分操作的 token 进行验证
 *              如需要更细粒度的管理权限，需要对当前 token 所对应角色的权限进行判断。
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class.getSimpleName());

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        String token = req.getHeader("token");

        // 浏览器会先发送options类型请求，该请求头中不包含token，直接返回。
        if (token == null) {
            return false;
        }

        if (redisUtil.hasKey(token)) {
            logger.info("token验证通过 => " + token);
            return true;
        }

        resp.setContentType("text/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(Resp.sendMsg(AppStatus.AUTH_FAILED));
        resp.getWriter().write(json);
        logger.error("token验证失败 => " + token);

        return false;
    }


}
