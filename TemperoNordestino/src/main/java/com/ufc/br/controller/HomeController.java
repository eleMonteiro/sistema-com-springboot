package com.ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.service.PratoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private PratoService service;
		
	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("pratos");
		
		mv.addObject("listaPratos", service.listarPratos()); 
		mv.addObject("endereco", "null");
		return mv;
    }
}
