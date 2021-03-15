package br.edu.ifrn.crudMateria.controladores;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crudMateria.dominio.Arquivo;
import br.edu.ifrn.crudMateria.dominio.Professor;
import br.edu.ifrn.crudMateria.repository.ArquivoRepository;
import br.edu.ifrn.crudMateria.repository.ProfessorRepository;

/**
 * Essa classe tem por função especificar a URL que será carregada quando
 * acessarmos a função cadastrar dos professores.
 * 
 * Utilização da anotação Spring @Controller para especificar que é um
 * controlador, que essa classe irá tratar das requisições a partir do acesso a
 * essa URL.
 * 
 * Utilização do @RequestMapping para mapear as solicitações web.
 *
 */

@Controller
@RequestMapping("/professores")
public class CadastroProfController {

	/**
	 * Implementação da interface ProfessorRepository
	 */

	@Autowired
	private ProfessorRepository professorRepository;

	/**
	 * Implementação da interface ArquivoRepository
	 */

	@Autowired
	private ArquivoRepository arquivoRepository;

	/**
	 * Essa classe tem por função especificar a URL que será carregada quando
	 * adentar na página de cadastro de professores.
	 * 
	 * @param model: Passar valores a serem visualizados pelo usuário.
	 * @return Carrega a página de cadastro dos profesores.
	 */

	@GetMapping("/cadastroProf")
	public String entrarCadastroProf(ModelMap model) {
		model.addAttribute("professor", new Professor());
		return "professores/cadastroProf";

	}

	/**
	 * @param professor: Essa anotação que acompanha a entidade (@Valid) serve para
	 *                   indicar que o objeto será validado, tendo como base as
	 *                   anotações de validação que atribuímos aos campos. A
	 *                   entidade Professor está ligada a esse método para que seus
	 *                   atributos possam ser acessados.
	 * @param result:    Usando o BindingResult, o objeto está sendo montado baseado
	 *                   nas informações adquiridas durante o cadastro.
	 * @param model:     Passar valores a serem visualizados pelo usuário.
	 * @param attr:      Redireciona os atributos especificados.
	 * @param arquivo:   Permite a implementação do arquivo.
	 * @param sessao:    Permite suporte a IDs se sessão para trabalhar com API.
	 * @return Carrega a página de cadastro dos professores.
	 */

	@PostMapping("/salvarProf")
	public String salvarProf(@Valid Professor professor, BindingResult result, ModelMap model, 
			                 RedirectAttributes attr,
			                 @RequestParam("file") MultipartFile arquivo, HttpSession sessao) {

		/**
		 * Uso de Try e Catch para tratamento de erros.
		 */

		try {
			
			/**
			 * Responsável pela validação dos dados
			 */
			
			if (result.hasErrors()) {

				return "professores/cadastroProf";

			}
			

			/**
			 * Pega o arquivo e normaliza o seu nome.
			 */

			if (arquivo != null && !arquivo.isEmpty()) {

				/**
				 * Normalizando o nome do arquivo
				 */

				String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());

				/**
				 * Pegando seus bytes.
				 */

				Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), 
						            arquivo.getBytes());

				/**
				 * Salvando o Ícone no Banco de Dados
				 */

				arquivoRepository.save(arquivoBD);

				/**
				 * Se o icone (arquivo) for diferente de nulo, como também seu id, e maior que
				 * 0, ou seja, se já tiver alguma imagem relacionada aquela matéria, a colocação
				 * de uma nova irá subscrever a anterior, deletando a mesma.
				 */

				if (professor.getIcone() != null && professor.getIcone().getId() != null
						&& professor.getIcone().getId() > 0) {

					/**
					 * Deletando a foto (arquivo) vinculado a matéria.
					 */

					arquivoRepository.delete(professor.getIcone());

				}

				/**
				 * Enviando para subscrever.
				 */

				professor.setIcone(arquivoBD);

			} else {

				/**
				 * Enviando para caso o campo esteja nulo.
				 */

				professor.setIcone(null);

			}

			/**
			 * Criptografando a senha usando BCrypt.
			 */

			String senhaCriptografada = new BCryptPasswordEncoder().encode(professor.getSenha());
			professor.setSenha(senhaCriptografada);

			/**
			 * Já serve para cadastro, salvando um novo professor ao Banco de Dados.
			 */

			professorRepository.save(professor);
			attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");

		} catch (IOException e) {

			e.printStackTrace();

		}

		/**
		 * Carrega a página de cadastro dos professores.
		 */

		return "redirect:/professores/cadastroProf";

	}

	/**
	 * Essa classe tem como função permitir a edição de um Professor já cadastrado.
	 * 
	 * @param idProfessor: Possbilita a edição dos professores cadastrados baseado
	 *                     em seu id.
	 * @param model:       Passar valores a serem visualizados pelo usuário.
	 * @param sessao:      Permite suporte a IDs se sessão para trabalhar com API.
	 * @return Carrega a página de cadastro dos professores.
	 */

	@GetMapping("/editarProf/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idProfessor, 
			                    ModelMap model, HttpSession sessao

	) {

		/**
		 * Realiza a busca pelo id usando a variável professorRepository que possui essa
		 * função em suas linhas de código.
		 */

		Professor p = professorRepository.findById(idProfessor).get();

		/**
		 * Mostra os atributos armazenados na variavel p (professor).
		 */

		model.addAttribute("professor", p);

		/**
		 * Carrega a página de cadastro dos professores.
		 */

		return "/professores/cadastroProf";

	}

	/**
	 * Possui a função de armazenar os valores a serem selecionados no optional que
	 * está no html de cadastro das matérias.
	 * 
	 * @return Retorna os valores especificados no método o qual o usuário que está
	 *         cadastrando deve escolher (apenas um).
	 */

	@ModelAttribute("materia")
	public List<String> getMateria() {

		/**
		 * Uma lista com as opções que o usuário que está cadastrando poderá selecionar.
		 */

		return Arrays.asList("Português", "POO", "Matemática", "PDS", "Biologia", "Historia", "Filosofia",
				"Sociologia");

	}

}
