package com.algaworks.osworks.domain.exception;

/*
 * Classe de Exceção para erros de Regras de Negócio
 * */

public class NegocioException extends RuntimeException {
	
	private static final long serialVersionUID = 1L; //controlar explicitamente a compatibilidade do .class pr serializaçao e o mesmo para desserialização, pois pd haver alterações.
	
	public NegocioException(String message) {
		super(message);
	}		
}
