package com.example.ecommerce.configs;

import com.example.ecommerce.dto.TransactionService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.*;
import org.springframework.web.filter.*;

import java.io.IOException;

@Component
public class CustomCorsFilter extends OncePerRequestFilter {
    @Resource(name = "getTransactionService")
    private TransactionService transactionservice;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.transactionservice.startTimeRequest();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(request.getHeader("Origin"));
        corsConfiguration.addAllowedMethod("GET, POST, PUT, DELETE");
        corsConfiguration.addAllowedHeader("Access-Control-Allow-Origin, Access-Control-Allow-Headers, Access-Control-Allow-Methods, " +
                "Accept, Authorization, Content-Type, Method, Origin, X-Forwarded-For, X-Real-IP");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);

        CorsProcessor corsProcessor = new DefaultCorsProcessor();
        corsProcessor.processRequest(corsConfiguration, request, response);
        if (CorsUtils.isCorsRequest(request)) {
            corsProcessor.processRequest(corsConfiguration, request, response);
        }
        filterChain.doFilter(request, response);
    }
}
