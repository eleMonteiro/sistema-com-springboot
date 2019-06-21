package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Item;
import com.ufc.br.model.Pedido;
import com.ufc.br.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository repository;

	public String salvarItem(List<Item> item) {
		repository.saveAll(item);
		return "Item Cadastrado.";
	}

	public List<Item> listarItens() {
		return repository.findAll();
	}

	public List<Item> listarItensPorPedido(Long idPedido) {
		return repository.findByPedidoId(idPedido);
	}
	
	public String excluirPrato(Long codigo) {
		Item item = this.buscarItemPorId(codigo);
		
		if( item == null)
			return "Não foi possivel remover o item.";
		repository.deleteById(codigo);
		return "Item Removido.";
	}

	public Item buscarItemPorId(Long codigo) {
		Item item = repository.getOne(codigo);

		if (item == null)
			return null;

		return item;
	}
	

	public List<Item> addIdPedido(Pedido pedido, List<Item> itens) {
		List<Item> itensNovo = new ArrayList<>();
		for (Item item : itens) {
			item.setPedido(pedido);
			itensNovo.add(item);
		}
		
		return itensNovo;
	}
}
