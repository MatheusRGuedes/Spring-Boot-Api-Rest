package com.algaworks.osworks.api.model;

import java.time.OffsetDateTime;

import com.algaworks.osworks.domain.model.OrdemStatusServico;

/*
 * Classe que serve de Representation model de nosso recurso (Domain Model).
 * */

public class OrdemServicoRepresentationModel {
	
	private Long id;
	private ClienteResumoRepresentationModel cliente; //mapeia apenas o id e o nome do cliente
	private String descricao;
	private OrdemStatusServico status;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFinalizacao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ClienteResumoRepresentationModel getCliente() {
		return cliente;
	}
	public void setCliente(ClienteResumoRepresentationModel cliente) {
		this.cliente = cliente;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public OrdemStatusServico getStatus() {
		return status;
	}
	public void setStatus(OrdemStatusServico status) {
		this.status = status;
	}
	
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	
	
}
