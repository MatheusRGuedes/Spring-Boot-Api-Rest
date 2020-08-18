package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.api.model.ClienteInputRepresentationModel;
import com.algaworks.osworks.api.model.ClienteRepresentationModel;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.service.CadastroClienteService;

/*
 * Classe controladora:
 * 
 * --> Responsável por receber requisições externas e enviar resposta
 * --> O Spring irá scannear e achar a classe controladora rest, fazendo o método funcionar
 * */

/* 	---- Conceitos ----
	EntityManager 		--> Interface do jakarta para fazer operações nas entidades (consulta, exclusão, etc)
	PersistenceContext	--> Anotação que instancia a variável manager para funcionar;
	Autowired			--> Cria uma instância de ClienteRepository na variável. Substitui todas as ligações que estavam com jpa usadas com o manager.
	PathVariable		--> Vincula o path variable no parâmetro
	RequestBody			--> Transforma o json da requisição em um Cliente
	@Valid				--> Ativa a validação do Jakarta Bean Validations
*/

@RestController
@RequestMapping("/clientes") 	//usado pr n repetir
public class ClienteController {
	
	//@PersistenceContext
	//private EntityManager manager;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	
	/*- Método que vai receber requisições via get, na URI: /clientes
	 	- Anotação do spring - pacote web
	 	- Por ser get, é um método "iden potente", pois a realização de 
	   	  requisições em sequências não geram um efeito colateral */
	
	@GetMapping
	public List<ClienteRepresentationModel> listar() {
		// busca todos os clientes (jpql, mas ger no final para sql), informando o tipo da classe de retorno, por fim retornando o resultado em lista
		// return manager.createQuery("from Cliente", Cliente.class).getResultList();
		return toModelList( clienteRepository.findAll() );
	}
	
	
	@GetMapping("/{clienteId}") //recurso único, passando um path variable
	public ResponseEntity<ClienteRepresentationModel> buscar( @PathVariable Long clienteId ) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if (cliente.isPresent()) {

			ClienteRepresentationModel clienteRepresentationModel = toModel(cliente.get());
			
			// usado para retribuir o cliente, e um codigo de status, no caso ok (200 pra cima)
			return ResponseEntity.ok( clienteRepresentationModel );
		}
		
		// retorn código de status 404 sem corpo
		return ResponseEntity.notFound().build();
		
		//return cliente.orElse(null); // extrai o cliente de optional ou retorna null
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)		//retorna codigo d status 201
	public ClienteRepresentationModel adicionar(@Valid @RequestBody ClienteInputRepresentationModel cliente) {
		
		Cliente clienteEntity = toEntity(cliente);
		
		return toModel( cadastroCliente.salvar(clienteEntity) );
	}
	
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<ClienteRepresentationModel> atualizar(@Valid @PathVariable Long clienteId, 
			@RequestBody ClienteInputRepresentationModel cliente) {
		
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente clienteEntity = toEntity(cliente);
		clienteEntity.setId(clienteId);  //setar id para identificar um update, se não, cria um nv registro
		
		cadastroCliente.salvar(clienteEntity);
		
		return ResponseEntity.ok( toModel(clienteEntity) ); // para atualização o código é 200
	}
	
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deletar(@PathVariable Long clienteId) { // como não retorna corpo, o tipo é void
		
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroCliente.excluir(clienteId);
		
		return ResponseEntity.noContent().build(); // retorno sem nada no corpo (código 204)
	}
		
	
	/* ------------- MÉTODOS DE MAPEAMENTO DE MODELOS --------------- */
	
	
	public ClienteRepresentationModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteRepresentationModel.class);
	}
	
	public List<ClienteRepresentationModel> toModelList(List<Cliente> clientes) {
		/*List<ClienteRepresentationModel> clienteRepresentationModel = new ArrayList<>();
		
		for (Cliente cliente : clientes) {
			clienteRepresentationModel.add( toModel(cliente) );
		}*/
		
		return clientes.stream()
				.map(cliente -> toModel(cliente))
				.collect(Collectors.toList());
	}
	
	public Cliente toEntity(ClienteInputRepresentationModel clienteInputRepresentationModel) {
		return modelMapper.map(clienteInputRepresentationModel, Cliente.class);
	}
}
