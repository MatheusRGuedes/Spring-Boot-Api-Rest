package com.algaworks.osworks.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.osworks.api.model.Comentario;
import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.model.OrdemStatusServico;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.repository.ComentarioRepository;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public OrdemServico criar(OrdemServico ordemServico) {
	
		Long idCliente = ordemServico.getCliente().getId();
		
		//primeiro extrai o cliente do Optional e se for != nulo retorna ele, se n retorna uma exceção
		// () -> - função 
		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado."));
		
		ordemServico.setCliente(cliente); //resolve o problema dos dados do cliente vierem nulos
		ordemServico.setStatus(OrdemStatusServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	

	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	
	public Comentario atualizarComentario(Long ordemServicoId, String descricao, Long comentarioId) {
		
		//depois testar usando uma classe que pussui as duas chaves (Embeddable)
		Comentario comentario = buscarComentario(comentarioId, ordemServicoId);
		comentario.setDescricao(descricao);
		
		return comentarioRepository.save(comentario);
	}
	
	
	public void deletarComentario(Long ordemServicoId, Long comentarioId) {
		Comentario comentario = buscarComentario(comentarioId, ordemServicoId);
		
		comentarioRepository.deleteById(comentario.getId());
	}
	
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setOrdemServico(ordemServico);
		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());
		
		return comentarioRepository.save(comentario);
	}
	
	
	public OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada."));
	}
	
	public Comentario buscarComentario(Long comentarioId, Long ordemServicoId) {
		Comentario comentario = comentarioRepository.buscarComentarioDeOrdemServico(comentarioId, ordemServicoId);
		
		if (comentario == null) {
			throw new EntidadeNaoEncontradaException("Ordem de serviço ou comentário não encontrado.");
		}
		
		return comentario;
	}
}
