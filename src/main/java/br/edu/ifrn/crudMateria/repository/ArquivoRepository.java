package br.edu.ifrn.crudMateria.repository;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.crudMateria.dominio.Arquivo;

/**
 * Interface responsável pela implementação dos arquivos no site.
 */

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
