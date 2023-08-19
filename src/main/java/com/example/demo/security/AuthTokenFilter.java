package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {

	private static final Logger LOG=LoggerFactory.getLogger(JwtUtils.class);

	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try {
			String jwt = this.parseJwt(request);
			if(jwt != null && this.jwtUtils.validateJwtToken(jwt) ) {
				//Como es valido el token le voy a autenticar
				String nombre = this.jwtUtils.getUsernameFromJwtToken(jwt);
				//Le autenticamos
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(nombre, null, new ArrayList<>()); 
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//Seteamos la autenticacion en la session
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}catch(Exception exception) {
			
			LOG.error("No se pudo realizar la autenticacion con el token enviado: {}", exception.getMessage());
			
		}
		
		filterChain.doFilter(request, response);

	}
	
	private String parseJwt(HttpServletRequest request) {
		String valorCompleto = request.getHeader("Authorization");
		if(StringUtils.hasText(valorCompleto)&& valorCompleto.startsWith("Bearer") ) {
			return valorCompleto.substring(7, valorCompleto.length());
		}
		return null;
	}
	
	
}
