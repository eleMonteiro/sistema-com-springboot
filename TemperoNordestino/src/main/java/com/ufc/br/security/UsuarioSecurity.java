package com.ufc.br.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ufc.br.model.Usuario;
import com.ufc.br.repository.UsuarioRepository;

@Repository
public class UsuarioSecurity implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = repository.findByEmail(email);

		if (usuario == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new User(usuario.getUsername(), usuario.getPassword(),
				true, true, true, true, usuario.getAuthorities());
	}

}
