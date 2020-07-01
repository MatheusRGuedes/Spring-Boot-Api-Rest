package com.algaworks.osworks.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.algaworks.osworks.api.model.Comentario;
import com.algaworks.osworks.domain.exception.NegocioException;

/*
 * Valid		--> Usado para ativar a validação dentro dos parametros e acima das propriedades. Valida de forma cascata (default), não só o cliente, mas seus atributos.
 * ManyToOne  	--> Indica uma associação muitas ordens de serviço para um cliente. Só esse já resolve sem o joinColumn
 * 			  	--> Sem expecificar nada, uma chave estrangeira é criada em OrdemServiço, associando o serviço ao cliente, que no caso seria o id do cliente como valor 
 * JoinColumn 	--> Cria a coluna FK chamada idCliente no bd, com o valor do id do cliente
 * Enumerated 	--> Anotação para determinar o tipo de valores constantes, o padrão é numeros
 * JsonProperty --> Anotação de jackson passando como parâmetro o access para somente leitura, não deixa o usuário informar
 * ConvertGroup --> Muda a validação em grupo default dos atributos d OrdemServico e Cliente (não fazendo sentido), para outro que valida só o id_Cliente.
 * 				--> Ex.: (from = Default.class, to = ValidationGroups.ClienteId.class)
 * mappedBy		--> Usado para especificar a coluna/campo em que a entidade se relaciona (Comentário).
 * */

@Entity
public class OrdemServico {
	
	/*As validações estão sendo feitas pela classe de Representação dessa entidade, pois os cadastros de ordem de serviço estão sendo feitas apenas pela API*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_Cliente")
	private Cliente cliente;
	
	@Enumerated(EnumType.STRING)
	private OrdemStatusServico status;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "preco")
	private BigDecimal preco;

	@Column(name = "data_abertura")
	private OffsetDateTime dataAbertura;

	@Column(name = "data_finalizacao")
	private OffsetDateTime dataFinalizacao;
	
	@OneToMany(mappedBy = "ordemServico")
	private List<Comentario> comentarios = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}	
	
	public OrdemStatusServico getStatus() {
		return status;
	}
	public void setStatus(OrdemStatusServico status) {
		this.status = status;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	public boolean podeSerFinalizada() {
		return OrdemStatusServico.ABERTA.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada(); 
	}
	
	public void finalizar() {
		// retorna falso se for aberta, caso contrário, verdadeiro
		
		if ( naoPodeSerFinalizada() ) { 
			throw new NegocioException("A Ordem de serviço não pode ser finalizada.");
		}
		
		this.setStatus(OrdemStatusServico.FINALIZADA);
		this.setDataFinalizacao(OffsetDateTime.now());
	}
}