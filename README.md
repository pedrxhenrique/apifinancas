# 💸 Projeto de Finanças Pessoais

Backend para controle financeiro simples, com cadastro e consulta de usuários, categorias e transações. Feito com **Spring Boot**, **PostgreSQL**, **Docker** e testes via **Postman**.

---

## Tecnologias

- Java 21 (OpenJDK)
- Spring Boot
- PostgreSQL
- Docker
- Postman

---

## Funcionalidades Atuais

- Salvar usuários, categorias e transações
- Buscar detalhes de usuários e categorias
- Pesquisa por nome de usuário
- Expection se o usuário já possuir o email cadastro no banco

---

## Estrutura Básica das Tabelas

- **Usuário**: id, nome, email, senha  
- **Categoria**: id, nome, descrição, usuario_id
- **Transação**: id, descrição, valor, data, categoria_id, usuario_id, tipo (receita/despesa)  

---
