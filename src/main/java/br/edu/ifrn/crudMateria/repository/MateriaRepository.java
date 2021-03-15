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

import br.edu.ifrn.crudMateria.dominio.Materia;

/**
 * Interface responsável por realizar a implementação do atributo "nome" nos
 * controllers da Matéria.
 */

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

	/**
	 * Realiza a busca pelo atributo "nome" da entidade Matéria.
	 * 
	 * @param nome: Atributo a ser procurado.
	 * @return O nome desejado.
	 */

	@Query("select m from Materia m where m.nome like %:nome% ")
	List<Materia> findByNome(@Param("nome") String nome);

}
