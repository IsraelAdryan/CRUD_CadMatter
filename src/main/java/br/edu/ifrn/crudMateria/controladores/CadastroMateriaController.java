package br.edu.ifrn.crudMateria.controladores;

/**
 * @author Israel Adryan 
 * @author Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crudMateria.dominio.Turma;
import br.edu.ifrn.crudMateria.repository.ArquivoRepository;
import br.edu.ifrn.crudMateria.dominio.Arquivo;
import br.edu.ifrn.crudMateria.dominio.Materia;
import br.edu.ifrn.crudMateria.dominio.Professor;
import br.edu.ifrn.crudMateria.dto.AutocompleteDTO;
import br.edu.ifrn.crudMateria.repository.TurmaRepository;
import br.edu.ifrn.crudMateria.repository.MateriaRepository;
import br.edu.ifrn.crudMateria.repository.ProfessorRepository;

/**
 * Essa classe tem por função especificar a URL que será carregada quando
 * acessarmos a função cadastrar das matérias.
 * 
 * Utilização da anotação Spring @Controller para especificar que é um
 * controlador, que essa classe irá tratar das requisições a partir do acesso a
 * essa URL.
 * 
 * Utilização do @RequestMapping para mapear as solicitações web.
 *
 */

@Controller
@RequestMapping("/materias")
public class CadastroMateriaController {

	/**
	 * Implementação da interface MateriaRepository.
	 */

	@Autowired
	private MateriaRepository materiaRepository;

	/**
	 * Implementação da interface ProfessorRepository, para criar relacionamento com
	 * essa entidade.
	 */

	@Autowired
	private ProfessorRepository professorRepository;

	/**
	 * Implementação da interface TurmaRepository.
	 */

	@Autowired
	private TurmaRepository turmaRepository;

	/**
	 * Implementação da interface ArquivoRepository, para possibilitar colocar
	 * arquivos.
	 */

	@Autowired
	private ArquivoRepository arquivoRepository;

	/**
	 * Essa classe tem com função criar uma nova matéria.
	 * 
	 * @param model: Passar valores a serem visualizados pelo usuário.
	 * @return Carrega a página de cadastro das matérias
	 * 
	 */

	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("materia", new Materia());
		return "materias/cadastro";

	}

	/**
	 * Essa classe tem por função salvar uma nova matéria baseado nas informações
	 * digitadas nos campos.
	 * 
	 * @param materia: Essa anotação serve para indicar que o objeto será validado
	 *                 tendo como base as anotações de validação que atribuímos aos
	 *                 campos. A entidade Matéria está ligada a esse método para que
	 *                 seus atributos possam ser acessados.
	 * 
	 * @param result:  Usando o BindingResult, o objeto está sendo montado baseado
	 *                 nas informações adquiridas durante o cadastro.
	 * 
	 * @param model:   Passar valores a serem visualizados pelo usuário.
	 * @param attr:    Redireciona os atributos especificados.
	 * @param arquivo: Permite a implementação do arquivo.
	 * @param sessao:  Permite suporte a IDs se sessão para trabalhar com API.
	 * @return Carrega a página de cadastro das matérias.
	 */

	@PostMapping("/salvar")
	public String salvar(@Valid Materia materia, BindingResult result, ModelMap model, RedirectAttributes attr,
			@RequestParam("file") MultipartFile arquivo, HttpSession sessao) {

		/**
		 * Uso de Try e Catch para tratamento de erros.
		 */

		try {

			/**
			 * Responsável pela validação dos dados
			 */

			if (result.hasErrors()) {

				return "materias/cadastro";

			}

			if (materia.getProfessor().getId() == 0) {

				model.addAttribute("msgErro", "Informe o professor da matéria.");
				return "materia/cadastro";
			}

			/**
			 * Pega o arquivo e normaliza o seu nome.
			 */

			if (arquivo != null && !arquivo.isEmpty()) {

				/**
				 * Normalizando o nome do arquivo.
				 */

				String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());

				/**
				 * Pegando seus bytes.
				 */

				Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), arquivo.getBytes());

				/**
				 * Salvando o icone (arquivo) no Banco de Dados.
				 */

				arquivoRepository.save(arquivoBD);

				/**
				 * Se o icone (arquivo) for diferente de nulo, como também seu id, e maior que
				 * 0, ou seja, se já tiver alguma imagem relacionada aquela matéria, a colocação
				 * de uma nova irá subscrever a anterior, deletando a mesma.
				 * 
				 */

				if (materia.getIcone() != null && materia.getIcone().getId() != null
						&& materia.getIcone().getId() > 0) {

					/**
					 * Deletando o icone (arquivo) vinculado a matéria.
					 */

					arquivoRepository.delete(materia.getIcone());

				}

				/**
				 * Enviando para subscrever.
				 */

				materia.setIcone(arquivoBD);

			} else {

				/**
				 * Enviando para caso o campo esteja nulo.
				 */

				materia.setIcone(null);

			}

			/**
			 * Cadastro - salva uma nova matéria ao Banco de Dados.
			 * 
			 * Se o cadastro foi feito corretamente, exibirá uma mensagem de sucesso.
			 */

			materiaRepository.save(materia);
			attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");

		} catch (IOException e) {

			e.printStackTrace();

		}

		/**
		 * Carrega a página de cadastro das matérias.
		 */

		return "redirect:/materias/cadastro";

	}

	/**
	 * Essa classe tem por função permitir a edição da matéria já cadastrada.
	 * 
	 * @param idMateria: Possbilita a edição das matérias cadastradas baseado em seu
	 *                   id.
	 * @param model:     Passar valores a serem visualizados pelo usuário.
	 * @param sessao:    Permite suporte a IDs se sessão para trabalhar com API.
	 * @return Irá retornar
	 */

	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idMateria, ModelMap model, HttpSession sessao

	) {

		/**
		 * Realiza a busca pelo id usando a variável materiaRepository que possui essa
		 * função em suas linhas de código.
		 */

		Materia m = materiaRepository.findById(idMateria).get();

		/**
		 * Mostra os atributos armazenados na variavel m (matéria).
		 */

		model.addAttribute("materia", m);

		/**
		 * Carrega a página de cadastro das matérias.
		 */

		return "/materias/cadastro";

	}

	/**
	 * Essa classe tem por função realizar um autocomplete dos Professores
	 * cadastrados no Banco de Dados.
	 * 
	 * @param termo: Parâmetro a ser retomado no html para realizar a operação do
	 *               autocomplete.
	 * @return Retorna os resultados encontrados enquanto o usuário digita, ou seja,
	 *         se procurar pelo nome deum professor, o autocomplete automaticamente
	 *         vai exibir opções para o texto que o usuário está digitando.
	 */

	@GetMapping("/autocompleteProfessores")
	@Transactional(readOnly = true)
	@ResponseBody
	public List<AutocompleteDTO> autocompleteProfessor(@RequestParam("term") String termo) {

		/**
		 * Cria uma lista nomeada professores (possuindo a entidade Professor e seus
		 * atributos) e realiza uma procura baseado em seu Nome, utilizando a interface
		 * professorRepository como auxilio, pois é nela que está a função para realizar
		 * essa ação.
		 */

		List<Professor> professores = professorRepository.findByNome(termo);

		/**
		 * Cria uma nova lista com autocomplete nomeada como "resultados".
		 */

		List<AutocompleteDTO> resultados = new ArrayList<>();

		/**
		 * Percorre a lista de professores e implementa o autocomplete, pegando os
		 * atributos de nome e id do professor.
		 */

		professores.forEach(p -> resultados.add(new AutocompleteDTO(p.getNome(), p.getId())));

		/**
		 * Retorna os resultados encontrados enquanto o usuário digita, ou seja, se
		 * procurar pelo nome de um professor, o autocomplete automaticamente vai exibir
		 * opções para o texto que o usuário está digitando.
		 */

		return resultados;

	}

	/**
	 * Essa classe tem por função realizar um autocomplete da em relacionamento com
	 * a entidade Turmas.
	 * 
	 * @param termo: Parâmetro a ser retomado no html para realizar a operação do
	 *               autocomplete.
	 * @return a lista de resultados.
	 */

	@GetMapping("/autocompleteTurmas")
	@Transactional(readOnly = true)
	@ResponseBody
	public List<AutocompleteDTO> autocompleteTurmas(@RequestParam("term") String termo) {

		/**
		 * Cria uma lista a partir da entidade Turma, nomeando a mesma de "turmas" e
		 * implementando a interface turmaRepository para fazer a busca a partir do
		 * nome, função está que está na interface citada.
		 */

		List<Turma> turmas = turmaRepository.findByNome(termo);

		/**
		 * Cria uma nova lista com autocomplete nomeada como "resultados".
		 */

		List<AutocompleteDTO> resultados = new ArrayList<>();

		/**
		 * Percorre a lista de turmas e implementa o autocomplete, pegando os atributos
		 * de nome e id da turma.
		 */

		turmas.forEach(p -> resultados.add(new AutocompleteDTO(p.getNome(), p.getId())));

		/**
		 * Retorna os resultados encontrados enquanto o usuário digita, ou seja, se
		 * procurar pelo nome de uma matéria, o autocomplete automaticamente vai exibir
		 * opções para o texto que o usuário está digitando.
		 */

		return resultados;

	}

	/**
	 * Essa classe tem por função adicionar uma turma ao relizar o cadastro de uma
	 * matéria, fazendo uma relacionamento com a entidade Turmas.
	 * 
	 * @param materia: Vai pegar informações da entidade Matéria.
	 * @param model:   Enviará a informação para a tela do usuário.
	 * @return Carrega página de cadastro das matérias.
	 */

	@PostMapping("/addTurma")
	public String addTurma(Materia materia, ModelMap model) {
		if (materia.getTurmas() == null) {
			materia.setTurmas(new ArrayList<>());

		}

		/**
		 * Adiciona uma Turma à Matéria.
		 */

		materia.getTurmas().add(materia.getTurma());

		/**
		 * Carrega a página de cadastro das matérias.
		 */

		return "/materias/cadastro";

	}

	/**
	 * Essa classe tem por função remover uma Turma do Banco de Dados vinculada a
	 * uma Matéria.
	 * 
	 * @param materia: Vai pegar informações da entidade Matéria.
	 * @param idTurma: Irá remover baseado no id da Turma.
	 * @param model:   Enviará os resultados para a tela do usuário.
	 * @return Carrega a página de cadastro das matérias.
	 */

	@PostMapping("/removerTurma/{id}")
	public String removerTurma(Materia materia, @PathVariable("id") Integer idTurma, ModelMap model) {

		/**
		 * Cria um nova turma e envia o id ser removido.
		 */

		Turma turma = new Turma();
		turma.setId(idTurma);

		/**
		 * Das turmas relacionadas com a Matéria, exclui a que foi selecionada baseado
		 * no id..
		 */

		materia.getTurmas().remove(turma);

		/**
		 * Carrega a página de cadastro das matérias.
		 */

		return "/materias/cadastro";

	}

	/**
	 * Possui a função de armazenar os valores a serem selecionados no optional que
	 * está no html de cadastro.
	 * 
	 * @return Retorna os valores especificados no método o qual o usuário que está
	 *         cadastrando deve escolher (apenas um).
	 */

	@ModelAttribute("nome")
	public List<String> getMaterias() {

		/**
		 * Uma lista com as opções que o usuário que está cadastrando poderá selecionar.
		 */

		return Arrays.asList("Português", "POO", "Matemática", "PDS", "Biologia", "Historia", "Filosofia",
				"Sociologia");

	}

}
