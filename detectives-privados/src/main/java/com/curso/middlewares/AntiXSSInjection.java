package com.curso.middlewares;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AntiXSSInjection implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletResponse resp = (HttpServletResponse) response;
		
		resp.setHeader("Content-Security-Policy", "default-src 'self'; style-src 'self' https://cdn.jsdelivr.net;");
//		style-src, script-src, img-src
		
		chain.doFilter(request, resp);
	}

}
