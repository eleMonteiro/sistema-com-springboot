package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Pedido;
import com.ufc.br.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
		
	public void salvarPedido(Pedido pedido) {
		repository.save(pedido);
	}
	
	public List<Pedido> listarPedidos() {
		return repository.findAll();
	}
	
	public List<Pedido> listarPedidosByUsuario(Long usuarioId) {
		return repository.findByUsuarioId(usuarioId);
	}

	public String excluirPedido(Long codigo) {
		Pedido pedido = buscarPedidoPorId(codigo);

		if (pedido == null)
			return "Não Foi Possivel Remover o Pedido.";

		repository.deleteById(codigo);

		return "Pedido Removido.";
	}

	public Pedido buscarPedidoPorId(Long codigo) {

		Pedido pedido = repository.getOne(codigo);

		if (pedido == null)
			return null;

		return pedido;
	}
}
