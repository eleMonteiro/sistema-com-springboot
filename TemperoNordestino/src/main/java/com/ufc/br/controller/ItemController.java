package com.ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ufc.br.service.ItemService;

@Controller
@RequestMapping("/itens")
public class ItemController {

	@Autowired
	private ItemService service = new ItemService();
	
}
