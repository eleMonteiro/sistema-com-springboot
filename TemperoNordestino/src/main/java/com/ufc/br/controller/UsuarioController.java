package com.ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Usuario;
import com.ufc.br.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@RequestMapping("/formulario")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("addUsuario");
		mv.addObject("usuario", new Usuario());
		return mv;
	}

	@RequestMapping("/cadastrar")
	public ModelAndView addUsuario(@Validated Usuario usuario, BindingResult result) {
		try {
			ModelAndView mv = new ModelAndView("addUsuario");

			if (result.hasErrors()) {
				return mv;
			}

			String msg = service.salvarUsuario(usuario);

			if (msg != "Usuario Cadastrado.") {
				mv.addObject("msg", msg);
				return mv;
			}

			mv = new ModelAndView("redirect:/");
			mv.addObject("message", "Usuario Cadastrado.");
			return mv;

		} catch (IllegalArgumentException e) {

		}
		return null;
	}

	@RequestMapping("/delete/{codigo}")
	public ModelAndView delete(@PathVariable Long codigo) {
		service.excluirUsuario(codigo);
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}

	@RequestMapping("/update/{codigo}")
	public ModelAndView update(@PathVariable Long codigo) {
		Usuario usuario = service.buscarUsuarioPorId(codigo);
		ModelAndView mv = new ModelAndView("/");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@RequestMapping("/login")
	public ModelAndView formLogin() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

}
