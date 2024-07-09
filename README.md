# PULSE TEST

Esse projeto atende um desafio de uma entrevista técnica da Pulse - Grupo Mateus. Trata-se de uma aplicação CRUD para persistir em um banco de dados H2 (em memória) produtos, tendo como atributos o nome, descrição e quantidade em estoque.

O projeto roda, por padrão, na porta 8080 e foi implementado o swagger para facilitar os testes da aplicação.

#### ENVIRONMENT

Versão do Java: openjdk 17.0.11
Versão do Springboot: 3.2.7
Sistema Operacional usado no desenvolvimento: Ubuntu 22.04.4 LTS
IDE usada no desenvovlimento: IntelliJ IDEA 2024.1.4 (Community Edition)

Para rodar a aplicação, é necessário entrar na pasta raíz do projeto e executar os seguintes comandos: `mvn clean install && mvn spring-boot:run`

#### BANCO DE DADOS 

As configurações do banco de dados são carregadas ao rodar a aplicação. Elas são encontradas no arquivo application.properties.

![alt text](https://github.com/iigorpaiva/pulse-test/blob/main/src/images/aplication-properties.png?raw=true)

#### API 

Essa aplicação irá rodar em `localhost:8080` com os seguintes endpoints:

- `/api/produto/editarProduto/{id}`: Editar o produto passando como parâmetro o id e um objeto DTO com os dados para serem salvos no objeto persistido com o id mencionado 
- `/api/produto/inserirProduto`: Um objeto é passado no body para ser persistido no banco de dados
- `/api/produto/buscarTodosProdutos`: Busca todos os produtos persistidos no banco de dados
- `/api/produto/buscarProduto/{id}`: Busca produto específico passando o id como parâmetro
- `/api/produto/deletarProduto/{id}`: Deleta o produto persistido passando o id como parâmetro

![alt text](https://github.com/iigorpaiva/pulse-test/blob/main/src/images/swagger-endpoints.png?raw=true)

#### TESTS

Foram desenvolvidos testes unitários e testes de integração (teste de endpoints). Para executar os testes, mas ta acessar a pasta raíz do projeto e executar o comando: `mvn test`.

Para facilitar a documentação e testes manuais, foi implementado o swagger. Enquanto o projeto estiver rodando, basta acessar a url `http://localhost:8080/swagger-ui/index.html` para ter acesso aos endpoints.