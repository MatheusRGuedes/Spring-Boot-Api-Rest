package com.algaworks.osworks.api.model;

import java.time.OffsetDateTime;

/*
 * Classe de representação do modelo de domínio Comentário. Não tem os atributos de ordem de serviço, pois é/são o comentário(s) da própria ordem de serviço.
 * */

public class ComentarioRepresentationModel {

	private Long id;
	private String descricao;
	private OffsetDateTime dataEnvio;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public OffsetDateTime getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(OffsetDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
}
