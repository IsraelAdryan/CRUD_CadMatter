package br.edu.ifrn.crudMateria.dto;

/**
 * @author Israel Adryan e Alexandre Bezerra
 * Data: 06/03/2021
 * @version 1.0
 */

/**
 * Essa é a classe responsável pelo autocomplete dos campos especificados.
 */

public class AutocompleteDTO {

	/**
	 * Possui atributos prórpios: label e value.
	 */

	private String label;

	private Integer value;

	/**
	 * É um constructor que pega todos os seus atributos.
	 */

	public AutocompleteDTO(String label, Integer value) {
		super();
		this.label = label;
		this.value = value;
	}

	/**
	 * Gets e Sets de todos os atributos.
	 */

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

}
