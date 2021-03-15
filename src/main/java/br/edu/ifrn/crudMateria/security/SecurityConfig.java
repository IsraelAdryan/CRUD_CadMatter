package br.edu.ifrn.crudMateria.security;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.crudMateria.dominio.Professor;
import br.edu.ifrn.crudMateria.service.ProfessorService;

/**
 * Classe rsponsável por limitar e/ou permitir o acesso a URLs baseado no tipo de usuário cadastrado
 * que realizou o login (Usuário ADMIN ou COMUM)
 */

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{ 
	
	/**
	 * Implementação da classe ProfessorService, para especificar que os usuários que consiguiram logar
	 * serão professores cadastrados no sistema.
	 */
	
	@Autowired
	private ProfessorService service;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/**
		 * Especifica que qualquer URL com os nomes especificados estão permitidos a qualquer usuário
		 * acessar.
		 */
		
		http.authorizeRequests()
		.antMatchers("/css/**", "/imagens/**", "/js/**").permitAll()
		.antMatchers("/publico/**").permitAll()
		
		/**
		 * Especifica que as URLs escolhidas, serão acessadas apenas pelo usuário ADMIN.
		 */
		
		.antMatchers("/professores/salvar", "/professores/editar/**", 
				"/professores/remover/**").hasAuthority(Professor.ADMIN)
		
		/*
		 * Especifica as URLs de login e erro de loging, como também, a função de lembrar os dados
		 * do usuário que conseguir fazer login.
		 */
		
		.anyRequest().authenticated()
		.and()
		    .formLogin()
		    .loginPage("/login")
		    .defaultSuccessUrl("/", true)
		    .failureUrl("/login-error")
		    .permitAll()
		.and()
		    .logout()
		    .logoutSuccessUrl("/")
		.and()
		    .rememberMe();
		
	}
	
	/**
	 * Permite a criptografia usando o BCrypt.
	 */
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	     auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
		
		
	}

}
