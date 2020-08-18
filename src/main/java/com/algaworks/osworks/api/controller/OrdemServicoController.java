package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.osworks.api.model.OrdemServicoInputRepresentationModel;
import com.algaworks.osworks.api.model.OrdemServicoRepresentationModel;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;

/*
 * ModelMapper --> biblioteca de terceiros já que não ha biblioteca no spring. Sua instância não é gerenciada pelo spring, pois não temos acesso ao cod fonte e n podemos adicionar como o componente
 * 			   --> Faz o mapeamento dos objetos, criando uma instância de OrdemServicoRepresentationModel, atribuindo os atributos com os do ordemServico
 * */

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	public ModelMapper modelMapper;
	
	@Autowired
	public OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	public GestaoOrdemServicoService gestaoOrdemServicoService;
	
	
	@GetMapping
	public List<OrdemServicoRepresentationModel> listar() {
		
		return toModel( ordemServicoRepository.findAll() );
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresentationModel adicionar(@Valid @RequestBody OrdemServicoInputRepresentationModel ordemServicoInputRepresentationModel) {
		
		OrdemServico ordemServico = toEntity(ordemServicoInputRepresentationModel);
		
		return toModel( gestaoOrdemServicoService.criar(ordemServico) );
	}
	
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoRepresentationModel> buscar(@PathVariable Long ordemServicoId) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId).orElse(null);
		
		if (ordemServico != null) {
			//Ao invés de setar cada atributo
			//OrdemServicoRepresentationModel representationModel = new OrdemServicoRepresentationModel();
			//ordemServicoRepresentationModel.setId(ordemServico.getId());  ...
			
			// Usa o ModelMapping
			OrdemServicoRepresentationModel ordemServicoRepresentationModel = toModel(ordemServico);
			
			return ResponseEntity.ok(ordemServicoRepresentationModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PutMapping("{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		
		gestaoOrdemServicoService.finalizar(ordemServicoId);
		
		//ou -> return ResponseEntity.noContent().build();
	}
	
	
	private OrdemServicoRepresentationModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoRepresentationModel.class);
	}
	
	private List<OrdemServicoRepresentationModel> toModel(List<OrdemServico> ordensServico) {
		
		//forma, o collect tranforma o stream em lista
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInputRepresentationModel ordemServicoInputRepresentationModel) {
		return modelMapper.map(ordemServicoInputRepresentationModel, OrdemServico.class);
	}
}
