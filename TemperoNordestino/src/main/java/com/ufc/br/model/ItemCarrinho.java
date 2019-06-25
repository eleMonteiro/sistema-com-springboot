package com.ufc.br.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ItemCarrinho {

	private static List<Item> itens = new ArrayList<Item>();
	private static Double total = 0.0;
	
	public List<Item> getItens() {
		return itens;
	}
	
	public void setItens(List<Item> item) {
		itens = item;
	}
	
	public Double getTotal() {
		return total;
	}
	
	public void setTotal(Double valorTotal) {
		total = valorTotal;
	}
	
	public void addItem(Item item) {
		itens.add(item);
		setTotal(getTotal() + item.getPrecoTotal());
	}
	
	public void deleteItem(int index) {
		setTotal(getTotal() - itens.get(index).getPrecoTotal());
		itens.remove(index);
	}

	public static void clearCarrinho() {
		total = 0.0;
		itens.clear();
	}
}