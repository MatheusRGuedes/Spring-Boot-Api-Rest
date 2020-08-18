package com.algaworks.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.osworks.api.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
	
	List<Comentario> findByOrdemServicoId(Long ordemServicoId);
	
	@Query("from Comentario c where c.id = ?1 and c.ordemServico.id = ?2") //JPQL
	Comentario buscarComentarioDeOrdemServico(Long comentarioId, Long ordemServicoId);
}
