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
import br.edu.ifrn.crudMateria.repository.MateriaRepository;

/**
 * Essa classe tem por função especificar a URL que será carregada quando
 * acessarmos a função buscar as matérias.
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
@RequestMapping("/materias")
public class BuscaMateriaController {

	/**
	 * Implementação da interface MateriaRepository.
	 */

	@Autowired
	private MateriaRepository materiaRepository;

	/**
	 * @return especifica a URL de busca das matérias.
	 * 
	 *         Utiliza @GetMapping para pegar as solicitações do tipo get.
	 */

	@GetMapping("/busca")
	public String entrarBusca() {
		return "materias/busca";

	}

	/**
	 * Essa classe tem como função realizar a busca dos professores já cadastrados.
	 * 
	 * @param nome:              Mostra o nome da matéria ao realizar a busca.
	 * @param mostrarTodosDados: Mostra todas as matérias cadastradas.
	 * @param sessao:            Permite suporte a IDs se sessão para trabalhar com
	 *                           API.
	 * @param model:             Passar valores a serem visualizados pelo usuário.
	 * @return Carrega a página de busca das matérias.
	 */

	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "mostrarTodosDados", required = false) Boolean mostrarTodosDados, HttpSession sessao,
			ModelMap model

	) {

		/**
		 * Acesssa a lista de matérias que já estavam cadastradas, ou seja, encontradas,
		 * quando realizar a busca.
		 * 
		 * Acessa a função de procurar pelo nome (findByNome) na lista de
		 * materiasEncontrados.
		 */

		List<Materia> materiasEncontrados = materiaRepository.findByNome(nome);

		/**
		 * Mostra os nomes das matérias encontradas.
		 */

		model.addAttribute("materiasEncontrados", materiasEncontrados);

		/**
		 * Caso a lista de mostrarTodosDados (que possui os atributos de cadastro da
		 * matéria) não possuir nada, ainda irá mostrar os resultados (Como não possuem
		 * nenhum valor, apareceram vazios).
		 */

		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);

		}

		/**
		 * Carrega a página de busca das matérias.
		 */

		return "materias/busca";

	}

	/**
	 * @param idMateria: Qefere-se ao id (que é um atributo da classe matéria) que
	 *                   será excluido.
	 * @param sessao:    Permite suporte a IDs se sessão para trabalhar com API.
	 * @param attr:      Redireciona os atributos especificados.
	 * @return Ao final recarrega a página de busca das matérias.
	 */

	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Integer idMateria, HttpSession sessao, RedirectAttributes attr

	) {

		/**
		 * Deleta uma matéria cadastrada baseada no seu id.
		 * 
		 * Logo após, mostra ao usuário (Caso não ocorra algum erro) a mensagem que a
		 * operação foi realizada com sucesso.
		 */

		materiaRepository.deleteById(idMateria);
		attr.addFlashAttribute("msgSucesso", "Matéria removida com sucesso");

		/**
		 * Carrega a página de busca das matérias.
		 */

		return "redirect:/materias/buscar";

	}

}
