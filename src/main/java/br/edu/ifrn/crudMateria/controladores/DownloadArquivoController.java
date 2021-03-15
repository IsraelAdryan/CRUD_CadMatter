package br.edu.ifrn.crudMateria.controladores;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.ifrn.crudMateria.dominio.Arquivo;
import br.edu.ifrn.crudMateria.repository.ArquivoRepository;

/**
 * Essa classe está responsável pela implementação do download de imagens no
 * Banco de Dados.
 */

@Controller
public class DownloadArquivoController {

	/**
	 * Implementação da interface ArquivoRepository.
	 */

	@Autowired
	private ArquivoRepository arquivoRepository;

	/**
	 * Essa classe tem por função permitir o download de imagens no site.
	 * 
	 * @param idArquivo: Permite acessar os atributos da entidade Arquivo.
	 * @param salvar:    Permite salvar a imagem desejada.
	 * @return Retorna o arquivo especificado.
	 */

	@GetMapping("/download/{idArquivo}")
	public ResponseEntity<?> downloadFile(@PathVariable Long idArquivo, @PathParam("salvar")
	                                      String salvar) {

		/**
		 * Carregando arquivo do Banco de Dados.
		 */

		Arquivo arquivoBD = arquivoRepository.findById(idArquivo).get();

		/**
		 * Salva o arquivo alterando seu nome.
		 */

		String texto = (salvar == null || salvar.equals("true"))
				? "attachment; filename=\"" + arquivoBD.getNomeArquivo() + "\""
				: "inline; filename=\"" + arquivoBD.getNomeArquivo() + "\"";

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(arquivoBD.getTipoArquivo()))
				.header(HttpHeaders.CONTENT_DISPOSITION, texto)
				.body(new ByteArrayResource(arquivoBD.getDados()));

	}

}
