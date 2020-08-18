create table comentario (
	id bigint not null auto_increment,
    id_ordem_servico bigint not null,
    descricao text not null,
    dataEnvio datetime not null,
    
    primary key (id)
);

alter table comentario add constraint fk_comentario_ordem_servico
foreign key (id_ordem_servico) references ordem_servico (id)