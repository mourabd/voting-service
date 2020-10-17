# Explicação do Serviço

Serviço consiste na criação de pautas (subjects) a serem votadas pelos associados (associates).
Para que as pautas sejam colocadas em votação é necessário criar uma sessão (voting session) vinculando a pauta em questão.

No momento da criação da sessão de votação é possível definir quando ela irá expirar. Caso uma data não seja informada, a aplicação assumirá o tempo padrão de 1 minuto.

Importante mencionar que a contabilização dos votos pode ser feita com a sessão aberta ou fechada, **porém o evento kafka só será publicado quando a sessão estiver fechada e a API de verificação de resultados for chamada.**

### Regras de negócio

A seguir estão listadas algumas regras de negócio da aplicação:

* Um mesmo CPF não pode ser cadastrado mais de uma vez para diferentes associados.
* Código da pauta não pode ser cadastrado mais de uma vez para diferentes pautas.
* Associado não pode votar mais de uma vez por pauta.
* Usuário não deve informar uma data anterior a data atual na abertura de uma sessão de votação.
* Usuário não deve tentar abrir uma sessão que já tenha sido aberta no passado.
* Usuário não pode votar se a sessão de votação estiver expirada.
* Associado pode estar inapto a votar (integração sistema externo: GET https://user-info.herokuapp.com/users/{cpf}).
    * Durante os testes percebi que algumas **poucas** vezes a API demorou demais a responder, gerando um erro de time out. Quando esse cenário ocorre eu optei por permitir que o usuário siga em frente para votar (ABLE_TO_VOTE), caso contrário o usuário ficaria preso. 

### Possíveis melhorias

* Kafka:
    - O ideal para esse cenário seria que o evento fosse publicado de forma automatizada, utilizando um scheduler para publicação do evento no momento em que o tempo de expiração fosse atingido.

* Performance:
    - Uma provável melhoria seria a utilização de bibliotecas reativas como, por exemplo, o spring webflux e mongo reactive com chamadas assíncronas "não blocantes". Ideal seria utilizar ferramentas de performance que comprovassem esse ganho.
    - Outra boa possibilidade seria a utilização de um in-memory data structure, como o Redis.

* DTOs como dependências:
    - Como já mencionado no [tech_decisions](tech_decisions.md), o ideal seria utilizarmos uma lib externa contendo nossos shared objects (Exemplo: dtos de request/response). Isso facilitaria muito o trabalho de quem consome o serviço.

* Docker:
    - O ideal seria ter esse serviço rodando em um container docker com as configurações de mongo, kafka, etc, descritas no `docker-compose.yml`
