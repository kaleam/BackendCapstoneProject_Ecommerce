package com.ecommerce.usermanagementservice.controllers;

import com.ecommerce.usermanagementservice.services.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    // This interceptor can be used to check authentication for incoming requests
    // You can implement preHandle, postHandle, and afterCompletion methods as needed

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // extract
            if (userService.validateToken(token)) { // Assuming null for userId, adjust as needed
                return true; // allow request to proceed
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized");
        return false;
    }

    // Other methods can be overridden as needed
}
