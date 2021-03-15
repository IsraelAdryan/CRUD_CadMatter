package br.edu.ifrn.crudMateria.repository;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crudMateria.dominio.Professor;

/**
 * Interface responsável por realizar a implementação do atributo "nome" e
 * "email) nos controllers do Professor.
 */

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

	/**
	 * Realiza a busca pelo atributo "nome" da entidade Professor.
	 * 
	 * @param nome: Atributo a ser procurado.
	 * @return O nome desejado.
	 */

	@Query("select p from Professor p where p.nome like %:nome% ")
	List<Professor> findByNome(@Param("nome") String nome);

	/**
	 * Realiza a busca pelo atributo "email" da entidade Professor.
	 * 
	 * @param nome: Atributo a ser procurado.
	 * @return O nome do email desejado.
	 */

	@Query("select p from Professor p where p.email like %:email% ")
	Optional<Professor> findByEmail(@Param("email") String email);

}
