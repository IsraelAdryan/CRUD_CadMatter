package br.edu.ifrn.crudMateria.dominio;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Essa é a entidade Professor.
 * 
 * A anotação @Id e @GeneratedValue são responsáveis por informar que o valor do
 * id será gerado automaticamente pelo Spring.
 * 
 * A anotação @NotBlack é para especificar que o campo no momento do cadastro
 * não pode ficar vazio.
 * 
 * A anotação @Column é para especificar que o atributo será uma coluna no Banco
 * de Dados.
 * 
 * A anotação @NotNull é para especificar que o campo não pode ficar nulo.
 * 
 * A anotação @Size é para especificar um limite máximo ou minimo de caracteres
 * a serem digitados no determinado campo no momento do cadastro.
 * 
 * A anotação @OnetoOne é para especificar que existe um relacionamento 1:1 com
 * outra entidade;
 */

@Entity
public class Professor {

	/**
	 * Especifica que o sistema irá possui dois tipo de usuário: ADMIN e COMUM.
	 */

	public static final String ADMIN = "ADMIN";
	public static final String PROF_COMUM = "COMUM";

	/**
	 * Possui 6 atributos prórpios: id, nome, email, senha, sexo e matéria.
	 * 
	 * Possui relacionamento 1:1 com: A entidade Arquivo.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "O campo nome é obrigatório.")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "O campo email é obrigatório.")
	@Column(nullable = false)
	private String email;

	@NotBlank(message = "O campo senha é obrigatório.")
	@Size(min = 2, message = "Uma senha deve ter pelo menos dois caracteres.")
	@Column(nullable = false)
	private String senha;

	@NotBlank(message = "O campo sexo é obrigatório.")
	@Column(nullable = false)
	private String sexo;

	@NotBlank(message = "O campo matéria é obrigatório.")
	@Column(nullable = false)
	private String materia;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Arquivo icone;

	@Column(nullable = false)
	private String perfil = PROF_COMUM;

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
		Professor other = (Professor) obj;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Arquivo getIcone() {
		return icone;
	}

	public void setIcone(Arquivo icone) {
		this.icone = icone;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
