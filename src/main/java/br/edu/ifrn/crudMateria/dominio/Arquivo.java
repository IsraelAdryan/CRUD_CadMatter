package br.edu.ifrn.crudMateria.dominio;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Essa é a entidade Arquivo.
 * 
 * A anotação @Id e @GeneratedValue são responsáveis por informar que o valor do id 
 * será gerado automaticamente pelo Spring.
 * 
 * A anotação @Lob permite receber valores maiores para determinado atributo.
 * 
 * A anotação @Column é para especificar que o atributo
 * será uma coluna no Banco de Dados. 
 */

@Entity
public class Arquivo {
	
	/**
	 * Possui 4 atributos prórpios: id, nomeArquivo, tipoArquivo e dados.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nomeArquivo;
	
	private String tipoArquivo;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] dados;

	/**
	 * É um constructor da entidade, que pega todos os seus atributos.
	 */
	
	public Arquivo(Long id, String nomeArquivo, String tipoArquivo, byte[] dados) {
		super();
		this.id = id;
		this.nomeArquivo = nomeArquivo;
		this.tipoArquivo = tipoArquivo;
		this.dados = dados;
	}

	/**
	 * Gets e Sets de todos os atributos.
	 */
	
	public Arquivo() {	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}
	
	
	
	
	
	
}
