package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufc.br.model.Item;
import com.ufc.br.model.ItemCarrinho;
import com.ufc.br.model.Pedido;
import com.ufc.br.model.Prato;
import com.ufc.br.model.Usuario;
import com.ufc.br.service.ItemService;
import com.ufc.br.service.PedidoService;
import com.ufc.br.service.PratoService;
import com.ufc.br.service.UsuarioService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	private ItemCarrinho carrinho = new ItemCarrinho();

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PratoService pratoService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ItemService itemService;

	@RequestMapping("adicionar/carrinho/{codigo}/{quantidade}")
	public ModelAndView addItemCarrinho(@PathVariable Long codigo, @PathVariable Long quantidade,
			RedirectAttributes redir) {
		Item item = new Item();
		Prato prato = pratoService.buscarPratoPorId(codigo);

		item.setPrato(prato);
		item.setQuantidade(quantidade);
		item.setPrecoTotal(prato.getPreco() * quantidade);
		carrinho.addItem(item);

		ModelAndView mv = new ModelAndView("redirect:/");

		redir.addFlashAttribute("listaItems", carrinho.getItens());

		return mv;
	}

	@RequestMapping("remover/carrinho/{codigo}")
	public ModelAndView removerItemCarrinho(@PathVariable int codigo, RedirectAttributes redir) {

		carrinho.deleteItem(codigo);

		ModelAndView mv = new ModelAndView("redirect:/");

		redir.addFlashAttribute("listaItems", carrinho.getItens());

		return mv;
	}

	@RequestMapping("/realizarPedido/{endereco}")
	public ModelAndView salvarPedido(@PathVariable String endereco) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Usuario usuario = usuarioService.buscaPorLogin(user.getUsername());

		Pedido pedido = new Pedido();
		pedido.setUsuario(usuario);
		pedido.setItems(carrinho.getItens());
		pedido.setTotal(carrinho.getTotal());
		pedido.setDataPedido(new Date());
		pedido.setEndereco(endereco);

		ModelAndView mv = new ModelAndView("redirect:/");

		pedidoService.salvarPedido(pedido);
		carrinho.setItens(itemService.addIdPedido(pedido, carrinho.getItens()));
		itemService.salvarItem(pedido.getItems());

		mv.addObject("message", "Pedido cadastrado!");
		carrinho.getItens().clear();
		return mv;
	}

	@RequestMapping("/pedidosUsuario")
	public ModelAndView historicoPedido() {

		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Usuario usuario = usuarioService.buscaPorLogin(user.getUsername());

		ModelAndView mv = new ModelAndView("pedidos");

		List<Pedido> pedidos = pedidoService.listarPedidosByUsuario(usuario.getId());

		if (pedidos == null) {
			pedidos = new ArrayList<>();
			pedidos.add(new Pedido());
		}

		mv.addObject("pedidos", pedidos);
		return mv;
	}

}
