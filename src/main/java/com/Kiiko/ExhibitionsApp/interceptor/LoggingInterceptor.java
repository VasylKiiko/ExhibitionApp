package com.Kiiko.ExhibitionsApp.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Request URL: {}", request.getRequestURL());
        log.info("Request method: {}", request.getMethod());
        log.info("Pre handle method is calling. User's session id - {}", request.getSession().getId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("Request URL: {}", request.getRequestURL());
        log.info("Post handle method is calling. User's session id - {}", request.getSession().getId());
    }
}
