package br.edu.ifrn.crudMateria.dominio;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Essa é a entidade Turma.
 * 
 * A anotação @Id e @GeneratedValue são responsáveis por informar que o valor do id 
 * será gerado automaticamente pelo Spring.
 * 
 * A anotação @NotBlack é para especificar que o campo no momento do cadastro
 * não pode ficar vazio. 
 * 
 * A anotação @Column é para especificar que o atributo
 * será uma coluna no Banco de Dados. 
 */

@Entity
public class Turma {
	
	/**
	 * Possui 2 atributos prórpios: id e nome.
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull (message = "O campo nome é obrigatório.")
	@Column(nullable = false)
	private String nome;

	/**
	 * Métodos HashCode e equals.
	 */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 * Gets e Sets de todos os atributos.
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	

}
