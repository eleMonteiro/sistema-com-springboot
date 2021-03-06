package com.ufc.br.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Prato;
import com.ufc.br.service.PratoService;

@Controller
@RequestMapping("/pratos")
public class PratoController {

	@Autowired
	private PratoService service;

	@RequestMapping("/formulario")
	public ModelAndView formulario() {
		ModelAndView mv = new ModelAndView("addPrato");

		mv.addObject(new Prato());
		mv.addObject("msg", null);
		mv.addObject("tipo", "Cadastrar");
		return mv;
	}

	@PostMapping("/cadastrar")
	public ModelAndView cadastrar(@Validated Prato prato, BindingResult result,
			@RequestParam(value = "imagem") MultipartFile imagem) {

		ModelAndView mv = new ModelAndView("addPrato");

		try {
			if (result.hasErrors()) {
				mv.addObject("tipo", "Cadastrar");
				return mv;
			}

			String msg = service.salvarPrato(prato, imagem);

			if (msg != "Prato Cadastrado.") {
				mv.addObject("msg", msg);
				mv.addObject("tipo", "Cadastrar");
				return mv;
			}

			mv = new ModelAndView("redirect:/");
			List<Prato> pratos = service.listarPratos();
			mv.addObject("listaPratos", pratos);

			return mv;
		} catch (MultipartException e) {
			mv.addObject("msg", "Imagem incorreta.");
			return mv;
		} catch (IllegalArgumentException e) {
			mv.addObject("msg", "Não foi possível adicionar prato.");
			return mv;
		} catch (PersistenceException e) {
			mv.addObject("msg", "Não foi possível adicionar prato.");
			return mv;
		} catch (ConstraintViolationException e) {
			mv.addObject("msg", "Não foi possível adicionar prato.");
			return mv;
		} catch (DataIntegrityViolationException e) {
			mv.addObject("msg", "Não foi possível adicionar prato.");
			return mv;
		}
	}

	@RequestMapping("excluir/{codigo}")
	public ModelAndView excluir(@PathVariable Long codigo) {
		service.excluirPrato(codigo);

		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}

	@RequestMapping("atualizar/{codigo}")
	public ModelAndView atualizar(@PathVariable Long codigo) {

		Prato prato = service.buscarPratoPorId(codigo);

		ModelAndView mv = new ModelAndView("addPrato");
		mv.addObject("tipo", "Editar");

		mv.addObject("prato", prato);

		return mv;
	}
}