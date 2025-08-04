package com.learn.SidClasses.Configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.SidClasses.Customs_Constants.CustomMesssage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        //we have create json and write json
        CustomMesssage cmsg=new CustomMesssage();
        cmsg.setMessage("Check your email or password !!!! Error:"+authException);
        cmsg.setSuccess(false);

        //manually write json to the response object
        ObjectMapper omap=new ObjectMapper();
        String jsonString=omap.writeValueAsString(cmsg);

        response.getWriter().println(jsonString);
    }
}
