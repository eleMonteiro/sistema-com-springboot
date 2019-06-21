package com.ufc.br.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ItemCarrinho {

	private List<Item> itens = new ArrayList<Item>();
	private Double total = 0.0;
	
	public List<Item> getItens() {
		return itens;
	}
	
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	public Double getTotal() {
		return total;
	}
	
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public void addItem(Item item) {
		itens.add(item);
		setTotal(getTotal() + item.getPrecoTotal());
	}
	
	public void deleteItem(int index) {
		setTotal(getTotal() - itens.get(index).getPrecoTotal());
		itens.remove(index);
	}
}