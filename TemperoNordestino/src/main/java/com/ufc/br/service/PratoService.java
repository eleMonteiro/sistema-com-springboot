package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Prato;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.util.ImagemPratoUtil;

@Service
public class PratoService {

	@Autowired
	private PratoRepository repository;

	public String salvarPrato(Prato prato, MultipartFile imagem) {

		if (!prato.getNome().matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\\\s]+$"))
			return "Nome do Prato Inválido.";

		if (prato.getPreco() <= 0)
			return "Preço do Prato Inválido.";

		if (prato.getDescricao().equals(""))
			return "Descrição do Prato Inválido.";

		if (imagem == null)
			return "Prato Precisa de Uma Imagem.";

		repository.save(prato);
		
		String caminho = "images/pratos/" + prato.getCodigo() + ".png";
		ImagemPratoUtil.salvarImagem(caminho, imagem);

		return "Prato Cadastrado.";
	}

	public List<Prato> listarPratos() {
		return repository.findAll();
	}

	public String excluirPrato(Long codigo) {
		Prato prato = this.buscarPratoPorId(codigo);

		if (prato == null)
			return "Prato não existe.";

		String caminho = "images/pratos/" +codigo+ ".png";

		ImagemPratoUtil.removerImagem(caminho);

		repository.deleteById(codigo);

		return "Prato Removido.";
	}

	public Prato buscarPratoPorId(Long codigo) {

		Prato prato = repository.getOne(codigo);

		if (prato == null)
			return null;

		return prato;
	}

}
