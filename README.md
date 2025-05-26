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

- Salvar usuário
- Buscar detalhes de usuário

---

## Estrutura Básica das Tabelas

- **Usuário**: id, nome, email, senha  
- **Categoria**: id, nome, tipo (receita/despesa)  
- **Transação**: id, descrição, valor, data, categoria_id, usuario_id  

---
