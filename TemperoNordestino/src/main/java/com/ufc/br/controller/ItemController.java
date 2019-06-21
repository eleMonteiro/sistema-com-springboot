package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Item;
import com.ufc.br.service.ItemService;

@Controller
@RequestMapping("/itens")
public class ItemController {

	@Autowired
	private ItemService service = new ItemService();
	
	@RequestMapping("/pedidos/{codigo}")
	public ModelAndView historicoPedido(@PathVariable Long codigo) {
		ModelAndView mv = new ModelAndView("itensPedidos");
		
		List<Item> itens = service.listarItensPorPedido(codigo);
		
		if( itens == null ) {
			itens = new ArrayList<>();
			itens.add(new Item());
		}
		
		mv.addObject("pratosPedido", itens);
		return mv;
	}
}
