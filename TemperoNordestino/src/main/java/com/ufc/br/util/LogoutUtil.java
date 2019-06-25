package com.ufc.br.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.ufc.br.model.ItemCarrinho;

@Component
public class LogoutUtil implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
		
		ItemCarrinho.clearCarrinho();
		response.sendRedirect("http://localhost:8080/");
	}

}
