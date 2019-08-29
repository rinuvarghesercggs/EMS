package com.EMS.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {

		try {
			String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
	
				if (auth != null) {
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} 
			 
			filterChain.doFilter(req, res);
		} catch (InvalidJwtAuthenticationException eje) {
	        //((HttpServletResponse) res).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			//FilterErrorResponse errorResponse = new FilterErrorResponse(e);
			final Map<String, Object> mapBodyException = new HashMap<>() ;
			mapBodyException.put("status"    , "Failed") ;
			mapBodyException.put("message"  , "Unauthorized token") ;
			((HttpServletResponse) res).setStatus(HttpServletResponse.SC_OK);
			//((HttpServletResponse) res).getWriter().write(convertObjectToJson(HttpServletResponse.SC_UNAUTHORIZED));
			final ObjectMapper mapper = new ObjectMapper() ;
			mapper.writeValue(res.getOutputStream(), mapBodyException) ;
	    }
	}

	private String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

}
