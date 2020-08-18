package com.algaworks.osworks.domain.exception;

/*
 * Classe que usa a classe NegocioException, quando não se encontrar uma ordem de serviço, quando se trabalha com comentários
 * */

public class EntidadeNaoEncontradaException extends NegocioException {
	
	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}
}
