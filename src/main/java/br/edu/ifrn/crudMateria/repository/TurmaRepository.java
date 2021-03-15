package br.edu.ifrn.crudMateria.repository;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crudMateria.dominio.Turma;

/**
 * Interface responsável por realizar a implementação do atributo "nome" e
 * "email) nos controllers da Turma.
 */

public interface TurmaRepository extends JpaRepository<Turma, Integer> {

	/**
	 * Realiza a busca pelo atributo "nome" da entidade Turma.
	 * 
	 * @param nome: Atributo a ser procurado.
	 * @return O nome desejado.
	 */

	@Query("select t from Turma t where t.nome like %:nome% ")
	List<Turma> findByNome(@Param("nome") String nome);

}
