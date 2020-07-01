package com.algaworks.osworks.api.model;

import javax.validation.constraints.NotBlank;

/*
 * Classe de representação do dínio Comentário para o input do usuário
 * */

public class ComentarioInputRepresentationModel {

	@NotBlank
	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
