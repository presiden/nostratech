package com.example.movie.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;

//		String url = req.getRequestURL().toString();
//		String queryString = req.getQueryString();
		
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String path = req.getRequestURI().substring(req.getContextPath().length());
	
		String resultPath = scheme + "://" + serverName + ":" + serverPort + path;
		System.out.println("Result path: " + resultPath);

		chain.doFilter(request, response);
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		
//		Map<String, Object> errorResponse = new HashMap<>();
//
//		errorResponse.put("status", HttpStatus.BAD_GATEWAY);
//		errorResponse.put("message", "The token is not valid.");
//		
//        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        res.getWriter().write(convertObjectToJson(errorResponse));


	}
	
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
	
}
