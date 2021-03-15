package br.edu.ifrn.crudMateria.controladores;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crudMateria.dominio.Materia;
import br.edu.ifrn.crudMateria.dominio.Professor;
import br.edu.ifrn.crudMateria.repository.MateriaRepository;
import br.edu.ifrn.crudMateria.repository.ProfessorRepository;

/**
 * Essa classe tem por função especificar a URL que será carregada quando
 * acessarmos a função buscar dos professores.
 * 
 * Utilização da anotação Spring @Controller para especificar que é um
 * controlador, que essa classe irá tratar das requisições a partir do acesso a
 * essa URL.
 * 
 * Utilização do @RequestMapping para mapear as solicitações web.
 *
 */

@SuppressWarnings("unused")
@Controller
@RequestMapping("/professores")
public class BuscaProfController {

	/**
	 * Implementação da interface ProfessorRepository.
	 */

	@Autowired
	private ProfessorRepository professorRepository;

	/**
	 * @return Carrega a página de busca dos professores.
	 * 
	 *         Utiliza @GetMapping para pegar as solicitações do tipo get.
	 */

	@GetMapping("/buscaProf")
	public String entrarBusca() {
		return "professores/buscaProf";

	}

	/**
	 * @param nome:              Mostra o nome do professor ao realizar a busca.
	 * @param mostrarTodosDados: Mostra todos os professores cadastrados.
	 * @param sessao:            Permite suporte a IDs se sessão para trabalhar com
	 *                           API.
	 * @param model:             Passar valores a serem visualizados pelo usuário.
	 * @return carrega a página de busca dos professores.
	 */

	@GetMapping("/buscarProf")
	public String buscarProf(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "mostrarTodosDados", required = false) Boolean mostrarTodosDados, HttpSession sessao,
			ModelMap model

	) {

		/**
		 * Acesssa a lista de professores que já estavam cadastrados, ou seja,
		 * encontrados quando realizar a busca.
		 * 
		 * Acessa a função de procurar pelo nome (findByNome) na lista de
		 * professoresEncontrados.
		 */

		List<Professor> professoresEncontrados = professorRepository.findByNome(nome);

		/**
		 * Mostra os nomes dos professores encontrados.
		 */

		model.addAttribute("professoresEncontrados", professoresEncontrados);

		/**
		 * Caso a lista de mostrarTodosDados (que possui os atributos de cadastro do
		 * professor) não possuir nada, ainda irá mostrar os resultados (Como não
		 * possuem nenhum valor, apareceram vazios).
		 */

		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);

		}

		/**
		 * Carrega a página de busca dos professores.
		 */

		return "professores/buscaProf";

	}

	/**
	 * @param idProfessor: Qefere-se ao id (que é um atributo da classe professor)
	 *                     que será excluido.
	 * @param sessao:      Permite suporte a IDs se sessão para trabalhar com API.
	 * @param attr:        Redireciona os atributos especificados.
	 * @return Ao final recarrega a página de busca dos professores.
	 */

	@GetMapping("/removerProf/{id}")
	public String remover(@PathVariable("id") Integer idProfessor, HttpSession sessao, RedirectAttributes attr

	) {

		/**
		 * Deleta um professor cadastrado baseado no seu id.
		 * 
		 * Logo após, mostra ao usuário (Caso não ocorra algum erro) a mensagem que a
		 * operação foi realizada com sucesso.
		 */

		professorRepository.deleteById(idProfessor);
		attr.addFlashAttribute("msgSucesso", "Professor removido com sucesso");

		/**
		 * Carrega a página de busca dos professores.
		 */

		return "redirect:/professores/buscarProf";

	}

}
