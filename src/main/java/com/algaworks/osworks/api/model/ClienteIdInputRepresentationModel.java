package com.algaworks.osworks.api.model;

import javax.validation.constraints.NotNull;

/*
 * Classe só para referenciar o id do cliente
 * */

public class ClienteIdInputRepresentationModel {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
