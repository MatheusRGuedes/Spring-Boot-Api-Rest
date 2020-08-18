package com.algaworks.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;

/*
 * ------------- Classe de regra de negócio ----------------
 * - Usada para cadastro(add, etc) de clientes, em vez de ficar tudo no controller.
 * - Separa a responsabilidade de regra de negócio, sendo uma boa prática
 * 
 * ------------- Duas visões/formas ------------------------- 
 * 1ª - Oque é processos de negócio, seja alterações no banco, passar pelo service. Já para consultas, passar só pelo repository.
 * 2ª - Deixar os processos de negócio e as consultas no service e não usar o repository no controller, apenas o service. 
 * 
 * --------------------- Conceitos -------------------------
 * Service --> Se torna um Conponente do spring, onde será instanciado um objeto dessa classe, sendo possível injetar por exemplo, no controller. Indica que é uma classe de domínio de serviço.
 * 
 * */

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public Cliente salvar(Cliente cliente) {
		
		String email = cliente.getEmail();
		
		Cliente clienteExistente = clienteRepository.findByEmail(email);
		
		//Não permite cadastro de cliente d msm email não deixando cadastrar ou atualizar
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
