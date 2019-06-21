package com.ufc.br.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImagemPratoUtil {

	public static void salvarImagem(String caminho, MultipartFile imagem) {

		File file = new File(caminho);

		try {
			FileUtils.writeByteArrayToFile(file, imagem.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removerImagem(String caminho) {
		File file = new File(caminho);
		FileUtils.deleteQuietly(file);
	}
	
}
