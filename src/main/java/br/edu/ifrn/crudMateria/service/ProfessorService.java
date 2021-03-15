package br.edu.ifrn.crudMateria.service;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.crudMateria.dominio.Professor;
import br.edu.ifrn.crudMateria.repository.ProfessorRepository;

@Service
public class ProfessorService implements UserDetailsService {

	/**
	 * Implementação da interface ProfessorRepository.
	 */
	
	@Autowired
	private ProfessorRepository repository;

	/**
	 * Realiza o login baseado nos atributos especificados.
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/**
		 * Realiza uma procura baseado pelo email do professor cadastrado.
		 * 
		 * Mostra uma mensagem para o usuário caso o professor não seja encontrado.
		 */
		
		Professor professor = repository.findByEmail(username)
				                 .orElseThrow(() -> 
				                      new UsernameNotFoundException("Professor não encontrado!"));
		
		/**
		 * Cria uma lista de autoridades para os perfis de professor, possibilitando que, com seu email
		 * e senha, possam realizar login.
		 */
		
		return new User(
		    professor.getEmail(),
		    professor.getSenha(),
		    AuthorityUtils.createAuthorityList(professor.getPerfil())
		
		);
		

	}
	
	
}
