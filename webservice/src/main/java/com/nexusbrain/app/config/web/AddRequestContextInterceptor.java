package com.nexusbrain.app.config.web;

import com.nexusbrain.app.config.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class AddRequestContextInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(AddRequestContextInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String eventId = UUID.randomUUID().toString().replace("-", "");
        MDC.put(AppConstants.EVENT_ID, eventId);
        LOG.debug("AddRequestContextInterceptor applied");
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        LOG.debug("AddRequestContextInterceptor afterCompletion");
        MDC.clear();
    }
}
