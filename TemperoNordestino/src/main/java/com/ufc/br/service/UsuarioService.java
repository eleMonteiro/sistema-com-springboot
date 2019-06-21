package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Papel;
import com.ufc.br.model.Usuario;
import com.ufc.br.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public String salvarUsuario(Usuario usuario) {	
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		
		Papel papel = new Papel();
		papel.setPapel("ROLE_CLIENTE");

		List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel);

		usuario.setPapeis(papeis);
		
		repository.save(usuario);
		
		return "Usuario Cadastrado.";
	}
	
	public String excluirUsuario(Long codigo) {
		Usuario usuario = this.buscarUsuarioPorId(codigo);

		if (usuario == null)
			return "Não Foi Possivel Remover o Usuario.";

		repository.deleteById(codigo);

		return "Usuario Removido.";
	}

	public Usuario buscarUsuarioPorId(Long codigo) {

		Usuario usuario = repository.getOne(codigo);

		if (usuario == null)
			return null;

		return usuario;
	}

	public Usuario buscaPorLogin(String username) {
		Usuario usuario = repository.findByEmail(username);

		if (usuario == null)
			return null;

		return usuario;
	}
}
