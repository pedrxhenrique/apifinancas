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

- Salvar usuários e categorias
- Buscar detalhes de usuários e categorias

---

## Estrutura Básica das Tabelas

- **Usuário**: id, nome, email, senha  
- **Categoria**: id, nome, descrição, usuario_id
- **Transação**: id, descrição, valor, data, categoria_id, usuario_id, tipo (receita/despesa)  

---
