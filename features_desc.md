# Explicação do Serviço

Serviço consiste na criação de pautas (subjects) a serem votados pelos associados (associates).
Para que as pautas sejam colocadas em votação é necessário criar uma sessão (voting session) vinculando a pauta em questão.

No momento da criação da sessão é possível definir quando ela irá expirar. Caso uma data não seja informada, a aplicação assumirá o tempo padrão de 1 minuto.

Importante mencionar que a contabilização dos votos pode ser feita com a sessão aberta ou fechada, **porém o evento kafka só será publicado quando a sessão estiver fechada e a API de verificação de resultados for chamada.** (Sabe-se que o ideal seria que o evento fosse publicado de forma automatizada) 

