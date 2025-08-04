package com.learn.SidClasses.Configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.SidClasses.Customs_Constants.CustomMesssage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAcessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        CustomMesssage cmsg=new CustomMesssage();
        cmsg.setMessage("You don't have permission to perform this operation");
        cmsg.setSuccess(false);
        String jsonString=new ObjectMapper().writeValueAsString(cmsg);
        response.getWriter().println(jsonString);

    }
}
