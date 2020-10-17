# Decisões Técnicas

Abaixo estão consolidados alguns pontos relevantes em relação a aplicação.

## Estrutura

Aplicação é basicamente definida em 3 camadas, sendo elas estruturadas nos seguintes packages:

### Controller (com.subjects.votingservice.controller)

* Responsável por expor os endpoints da aplicação.
* **Não é esperado que contenha lógica de negócios**, servindo apenas como ponto de entrada de requisições HTTP.
* **Esperado que receba e produza objetos do tipo DTO (data transfer object), não havendo assim um acoplamento com o banco de dados.**
* Concentra a [documentação](http://localhost:8081/api/voting-service/swagger-ui.html) das APIs por meio de anotações.
* Deve comunicar-se **exclusicavamente** com a camada de serviços.

### Service (com.subjects.votingservice.service)

* Tem como principal responsabilidade aplicar as diversas validações referentes a regras de negócio (mais detalhes na seção [regras de negócio](features_desc.md)).
* É responsável por se comunicar com a camada de persistência e/ou com classes que contenham chamadas para serviços externos.
* Responsável por fazer o mapeamento de DTOS para entidades e vice versa antes de se comunicar com as demais camadas. 

### Repository (com.subjects.votingservice.repository)

* Camada que possui a exclusividade da comunicação com a base de dados, devendo manipular apenas objetos do tipo entidade (entity).
* Deve mapear métodos que indiquem consultas (queries) e persistência de entidades. 


#### Outros packages relevantes

* Configuration (com.subjects.votingservice.configuration): Responsável pela criação de @Beans gerenciáveis pelo Spring. Algumas configurações relevantes: 
    * kafka
    * mongo
    * rest template / rest template response error handler
    * swagger
    * global exception handler
* Integration (com.subjects.votingservice.integration): Comunicação serviços externos.
* Model (com.subjects.votingservice.model): Entidades a serem persistidas no banco de dados.
* Shared (com.subjects.votingservice.shared):

**Idealmente esse package deveria ser compartilhado na forma de dependência (maven/gradle) com quaisquer applicação com a qual se comunique.
Isso facilitaria para quem chama saber e respeitar a estrutura de request/response mapeados nos DTOs.** 

## Build da Aplicação

O .jar da aplicação só é gerado após o build passar pelos seguintes critérios:

* Análise do código pelo [PMD](https://pmd.github.io/) 
* Análise do código pelo [chekstyle](https://checkstyle.sourceforge.io/)
* Testes unitários: idealmente deve-se buscar 100% de cobertura. Essa aplicação conta com 68 testes unitários, atingindo uma cobertura de 82% das classes.

## Testes da Aplicação

A fim de facilitar os testes da aplicação, foi adicionado na pasta `resources` da estrutura de testes uma collection do postman com o nome `Sicredi Voting Service APIs.postman_collection.json`.

## Logs da Aplicação

Logs foram configurados seguindo o padrão do arquivo `logback-spring.xml`, localizado na pasta `resources`.

## Configuration properties

Essa aplicação utiliza o conceito de properties por ambiente. Nesse caso estamos utilizando o ``application.yml`` contendo as properties padrão para todos os ambientes e o ``application-local.yml`` contendo properties específicas do ambiente local.
A ideia é que as properties dos demais ambientes sejam externalizadas da aplicação, evitando o trabalho de redeploy para o caso de editar algum valor de property.