package com.ufc.br.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ufc.br.security.UsuarioSecurity;
import com.ufc.br.util.LogoutUtil;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioSecurity security;

	@Autowired
	private LogoutUtil logoutUtil;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		
		.antMatchers("/").permitAll()
		.antMatchers("/usuarios/formulario").permitAll()
		.antMatchers("/usuarios/cadastrar").permitAll()
		.antMatchers("/usuarios/delete/{codigo}").hasRole("CLIENTE")
		.antMatchers("/usuarios/update/{codigo}").hasRole("CLIENTE")
		.antMatchers("/pratos/formulario").hasRole("ADMIN")
		.antMatchers("/pratos/cadastrar").hasRole("ADMIN")
		.antMatchers("/pratos/excluir/{codigo}").hasRole("ADMIN")
		.antMatchers("/pratos/atualizar/{codigo}").hasRole("ADMIN")
		.antMatchers("/pedido/adicionar/carrinho/{codigo}/{quantidade}").hasRole("CLIENTE")
		.antMatchers("/pedido/remover/carrinho/{codigo}").hasRole("CLIENTE")
		.antMatchers("/pedido//realizarPedido/{endereco}").hasRole("CLIENTE")
		.antMatchers("/pedido/pedidoUsuario").hasRole("CLIENTE")
		.antMatchers("/itens/pedidos/{codigo}").hasRole("CLIENTE")
		
		
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/usuarios/login").permitAll().defaultSuccessUrl("/")
		
		.and()
		.logout().logoutSuccessHandler(logoutUtil);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(security).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/img/**");
	}

}
