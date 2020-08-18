package com.algaworks.osworks.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Classe para fazer a configuração de injeção de dependência de ModelMapper
 * 
 * ModelMapper	 --> internamente ele executa os métodos getters. Trabalha com o conceito de converção
 * Configuration --> Componente spring de configuração de beans
 * Bean			 --> Ele é instanciado e configurado como uma injeção de dependencias, sendo gerenciado pelo spring
 * */

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
