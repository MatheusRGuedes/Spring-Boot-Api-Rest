package com.algaworks.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.osworks.domain.model.Cliente;

/* - Interface que armazena métodos de persistência, servindo como repositório
 * - Ela herda outra interface que informa o tipo da entidade e do id
 * 
 * ----- CONCEITOS -----
 * 
 * Repository --> Indica que é um componente do spring. É gerenciado pelo framework, podendo ser usada em outras classes, para enjeção de dependências
 * 
 * */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	// filtra por nome, o Spring Data JPA implementa, automático;
	List<Cliente> findByNome(String nome);
	
	// filtra se o nome contêm a string
	List<Cliente> findByNomeContaining(String nome);
	
	// filtra um cliente por email
	Cliente findByEmail(String email);
	
}
