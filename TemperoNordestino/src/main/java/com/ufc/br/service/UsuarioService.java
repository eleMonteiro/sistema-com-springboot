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
		
		if (!usuario.getNome().matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\\\\\\\\\\\\\\\s]+([ ][A-ZA-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\\\\\\\\\\\\\\\s]+)*$"))
			return "Nome Inválido.";
		
		if(!usuario.getCpf().matches("[0-9]{11}"))
			return "CPF Inválido.";
		
		if(usuario.getEndereco().equals("") || usuario.getEndereco().matches("[0-9]"))
			return "Endereço Inválida.";
		
		if(!usuario.getEmail().matches("^[a-zA-Z]+[0-9.]*@[a-zA-Z]+.[a-zA-Z]+.([a-zA-Z]+)?$"))
			return "Email Inválido.";
		
		if(!usuario.getSenha().matches("[A-Za-z0-9]+$"))
			return "Senha Inválida.";
		
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
			return "Usuario não encontrado.";

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
