package com.algaworks.osworks.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.exception.NegocioException;

/*
 * Classe Tratadora de Exceptions
 * 
 * ControllerAdvice				   --> Componente do spring que é chamado quando ocorre uma exceção em qualquer Controller, retornando uma resposta mais adequada
 * ResponseEntityExceptionHandler  --> Classe que auxilia no básico de tratamento de exceptions
 * MessageSource 				   --> Interface do spring que resolve as mensagens do messages.properties, com o auto wired para atribuir uma instância na variável
 * ExceptionHandler				   --> Determina caso uma exceção for lançada por algum controlador pelo NegocioException, execute o método com a anotação
 * */

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
		
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {   //WebRequest --> inferface q representa uma requisição da web
		var status = HttpStatus.BAD_REQUEST;
		
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	// Método a ser chamado para argumento não válido
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var campos = new ArrayList<Problema.Campo>();
		
		//Percorre a lista de todos os erros globais e dos campos
		for(ObjectError erro : ex.getBindingResult().getAllErrors()) {
			
			//erro.getObjectName(); 	--> retorna cliente
			//erro.getDefaultMessage() 	--> retorna a msg padrão
			
			String nome = ((FieldError) erro).getField(); 											// converte e pega o campo
			String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());		// pega a mensagem traduzida, passando o erro e pegando a lingua/região
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campos estão inválidos. "
				+ "Faça o prenchimento correto e tente novamente.");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		
		// executa um método mais genérico e customizável, passando o problema como o corpo
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	
}
