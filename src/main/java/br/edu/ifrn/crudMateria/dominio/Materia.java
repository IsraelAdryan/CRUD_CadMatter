package br.edu.ifrn.crudMateria.dominio;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Essa é a entidade Matéria.
 * 
 * A anotação @Id e @GeneratedValue são responsáveis por informar que o valor do
 * id será gerado automaticamente pelo Spring.
 * 
 * A anotação @NotBlank é para especificar que o campo no momento do cadastro
 * não pode ficar vazio.
 * 
 * A anotação @Column é para especificar que o atributo será uma coluna no Banco
 * de Dados.
 * 
 * A anotação @Valid é para realizar a validação do professor, sendo que esse
 * atributo será resgatado da entidade Professor.
 * 
 * A anotação @Size é para especificar um limite máximo ou minimo de caracteres
 * a serem digitados no determinado campo no momento do cadastro.
 * 
 * A anotação @ManytoMany é para especificar um relacionamento N:N com outra
 * entidade.
 * 
 * A anotação @OnetoOne é para especificar que existe um relacionamento 1:1 com
 * outra entidade;
 */

@Entity
public class Materia {

	/**
	 * Possui 5 atributos próprios: id, nome, professor, carga e dias
	 * 
	 * Possui relacionamento N:N com: A entidade Turmas.
	 * 
	 * Possui relacionamento 1:1 com: A entidade Arquivo.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "O campo matéria é obrigatório.")
	@Column(nullable = false)
	private String nome;

	@ManyToOne(optional = false)
	private Professor professor;

	@NotBlank(message = "O campo carga é obrigatório.")
	@Size(max = 3, message = "A carga deve ter no máximo três caracteres.")
	@Column(nullable = false)
	private String carga;

	@NotBlank(message = "O campo dias é obrigatório.")
	@Size(max = 1, message = "O campo dias deve ter no máximo um caractere.")
	@Column(nullable = false)
	private String dias;

	@ManyToMany
	private List<Turma> turmas;

	@Transient
	private Turma turma;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Arquivo icone;

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
		Materia other = (Materia) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * Gets e Sets
	 * 
	 * @return Retorna cada atributo da entidade Matéria.
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

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public String getCarga() {
		return carga;
	}

	public void setCarga(String carga) {
		this.carga = carga;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Arquivo getIcone() {
		return icone;
	}

	public void setIcone(Arquivo icone) {
		this.icone = icone;
	}

}