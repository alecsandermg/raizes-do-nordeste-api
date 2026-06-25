# Raízes do Nordeste API

## Sobre o projeto

O Raízes do Nordeste API é um sistema desenvolvido para gerenciamento de pedidos de uma rede de restaurantes. A aplicação permite o cadastro de usuários, autenticação com JWT, consulta de cardápio, criação e acompanhamento de pedidos, controle de estoque, programa de fidelidade e processamento de pagamentos utilizando um serviço mock.

O projeto foi desenvolvido como parte da disciplina de Projeto Integrador do curso de Análise e Desenvolvimento de Sistemas.

---

# Requisitos

Antes de executar o projeto, é necessário possuir os seguintes softwares instalados:

* Java 21
* Maven 3.9 ou superior
* PostgreSQL 17
* Git (opcional)

Principais dependências utilizadas:

* Spring Boot 3
* Spring Security
* Spring Data JPA
* PostgreSQL Driver
* JWT (JSON Web Token)
* Springdoc OpenAPI (Swagger)

---

# Configuração das variáveis de ambiente

Crie um arquivo chamado `.env` na raiz do projeto utilizando como base o arquivo `.env.example`.

Exemplo:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=raizes_db
DB_USERNAME=postgres
DB_PASSWORD=sua_senha

JWT_SECRET=sua_chave_secreta
JWT_EXPIRATION=86400000
```

Depois, configure o arquivo `application.properties` utilizando essas informações.

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
```

---

# Instalação das dependências

Após clonar o repositório, execute:

```bash
mvn clean install
```

O Maven fará o download de todas as dependências necessárias.

---

# Criação do banco de dados

Criar o banco PostgreSQL:

```sql
CREATE DATABASE raizes_db;
```

Como o projeto utiliza o Hibernate com geração automática das tabelas (`spring.jpa.hibernate.ddl-auto=update`), não é necessário executar scripts de migration.

As tabelas serão criadas automaticamente na primeira execução da aplicação.

Caso seja necessário utilizar um usuário administrador para testes, ele poderá ser cadastrado pela API ou inserido diretamente no banco de dados.

---

# Executando a aplicação

No terminal, execute:

```bash
mvn spring-boot:run
```

Ou, caso utilize uma IDE como Eclipse ou IntelliJ IDEA, basta executar a classe:

```
RaizesDoNordesteApiApplication
```

Após a inicialização, a API ficará disponível em:

```
http://localhost:8080
```

---

# Documentação da API

A documentação da API é disponibilizada automaticamente através do Swagger.

Acesse:

```
http://localhost:8080/swagger-ui.html
```

ou

```
http://localhost:8080/swagger-ui/index.html
```

Pelo Swagger é possível visualizar todos os endpoints disponíveis e realizar testes diretamente pelo navegador.

---

# Testes

Durante o desenvolvimento, os testes foram realizados utilizando:

* Swagger/OpenAPI
* Postman

Os principais cenários testados incluem:

* Cadastro de usuários
* Login e autenticação JWT
* Controle de permissões por perfil (ADMIN e CLIENTE)
* Cadastro e consulta de produtos
* Controle de estoque
* Criação de pedidos
* Atualização do status dos pedidos
* Cancelamento de pedidos com devolução ao estoque
* Processamento de pagamento (Mock)
* Programa de fidelidade
* Resgate de pontos
* Registro de auditoria

Até o momento, o projeto não possui testes automatizados utilizando JUnit.

---

# Estrutura do projeto

```
src
 ├── api
 │     ├── controller
 │     └── dto
 │
 ├── application
 │     └── service
 │
 ├── domain
 │     ├── entity
 │     └── enums
 │
 ├── infrastructure
 │     ├── config
 │     ├── repository
 │     └── security
 │
 └── resources
       └── application.properties
```

---

# Autor

Alecsander Muller Gomes

Curso de Análise e Desenvolvimento de Sistemas.
