package br.edu.ifrn.crudMateria.controladores;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Essa classe está responsável por indicar a URL inicial do site, bem como a URL de login
 * e possivel erro de login, informando uma mensagem de erro.
 */

@Controller
public class inicioController {
	
	/**
	 * Especifica a URL de inicio do site.
	 * @return Carrega a página de inicio do site.
	 */
	
	@GetMapping("/")
	public String inicio() {
		
		return "inicio";
		
	}

	
	/**
	 * Especifica a URL de login do site que será carregado antes da página inicial.
	 * @return Carraga a página de login.
	 */
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
		
	}
	
	/**
	 * Especifica a URL que será carregada caso o login não seja efetuado com sucesso.
	 * @param model: Mostra a mensagem de erro na tela do usuário.
	 * @return Carrega a página de login.
	 */
	
	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("msgErro", "Login ou senha incorretos. Tente novamente.");
		return "login";
		
	}
}
