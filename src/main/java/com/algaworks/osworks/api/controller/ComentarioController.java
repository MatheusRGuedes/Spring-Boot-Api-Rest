package com.algaworks.osworks.api.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.algaworks.osworks.api.model.Comentario;
import com.algaworks.osworks.api.model.ComentarioInputRepresentationModel;
import com.algaworks.osworks.api.model.ComentarioRepresentationModel;
import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	
	

	
	@GetMapping
	public List<ComentarioRepresentationModel> listar(@PathVariable Long ordemServicoId) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada."));
		
		//List<Comentario> listaComentarios = new ArrayList<>();
		//listaComentarios = comentarioRepository.findByOrdemServicoId(ordemServico.getId());
		
		//getComentarios() daz uma persistência de dados retornando as entidades filhas que estão dentro do pai, isso por causa do OneToMany
		return toCollectionModel( ordemServico.getComentarios() );
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioRepresentationModel adicionar(@PathVariable Long ordemServicoId, 
			@Valid @RequestBody ComentarioInputRepresentationModel comentarioInput) {
		
		Comentario comentario = gestaoOrdemServicoService.adicionarComentario(ordemServicoId, 
				comentarioInput.getDescricao());
		
		return toModel( comentario );
	}
	
	
	@PutMapping("/{comentarioId}")
	public ComentarioRepresentationModel atualizar(@PathVariable Long ordemServicoId, 
			@Valid @RequestBody ComentarioInputRepresentationModel comentarioInput, 
			@PathVariable Long comentarioId) {
		
		Comentario comentario = gestaoOrdemServicoService.atualizarComentario(ordemServicoId, comentarioInput.getDescricao(), comentarioId);
		
		return toModel( comentario );
	}
	
	@DeleteMapping("/{comentarioId}")
	public ResponseEntity<Void> deletar(@PathVariable Long ordemServicoId, 
			@PathVariable Long comentarioId) {
		
		gestaoOrdemServicoService.deletarComentario(ordemServicoId, comentarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	
	public ComentarioRepresentationModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioRepresentationModel.class);
	}
	
	public List<ComentarioRepresentationModel> toCollectionModel(List<Comentario> listaComentarios) {
		List<ComentarioRepresentationModel> collectionModel = new ArrayList<>();
		
		for (Comentario comentario : listaComentarios) {
			collectionModel.add( toModel(comentario) );
		}
		
		return collectionModel;
	}
}