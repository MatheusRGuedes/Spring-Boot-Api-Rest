package com.algaworks.osworks.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
 * Classe usada para servir de Representation Model da entrada de dados.
 * */

public class OrdemServicoInputRepresentationModel {

	@Valid
	@NotNull
	private ClienteIdInputRepresentationModel cliente;	//assim terá que passar como objeto informando o id
	//ou assim --> private Long idCliente;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private BigDecimal preco;
	
	public ClienteIdInputRepresentationModel getCliente() {
		return cliente;
	}
	public void setCliente(ClienteIdInputRepresentationModel cliente) {
		this.cliente = cliente;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
}
