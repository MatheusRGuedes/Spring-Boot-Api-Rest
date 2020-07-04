# Spring-Boot-Api-Rest
Prejeto Spring Boot para monitorar ordens de serviço.

## Tecnologias
- Java 11;
- Spring 4;
- Spring Boot;
- Spring Data JPA para persistência de dados;
- Maven como configuração de projeto para gerenciamento de dependências;
- MySql Data Base.

## Rotas
- <code>localhost:8080/clientes</code>
- <code>localhost:8080/ordens-servico</code>
- <code>localhost:8080/ordens-servico/1/comentarios</code> (comentários da ordem de serviço com id 1, por exemplo)

## Funcionalidades atuais disponíveis
- Listar, buscar, criar, atualizar e deletar um cliente;
- Listar, buscar, criar e atualizar uma ordem de serviço;
- Listar, criar, atualizar e deletar um comentário.

### Observação 
- As queries para criação das tabelas se encontram na pasta SQL. Você pode usar o Flyway(dependência sem encontra no pom.xml), que é uma ferramenta de controle de versão para fazer a migração das tabelas criadas, ou qualquer alteração feita no banco, para o banco MySql ou o banco de sua preferência.

- Para isso, basta criar o seguinte diretório na pasta <code>src/main/resources</code>:<br/>
<code>db/migration</code><br/> Em seguida, criar cada arquivo de versão, seguindo o padrão <code>V00n__descrição</code>, onde o n é o número da versão. [Detalhes aqui] - (https://flywaydb.org/documentation/migrations)

- Outra forma é de criação das tabelas automáticamente, que já está configurado no arquivo <code>application.properties</code>, não precisando se preocupar com a outra forma acima.