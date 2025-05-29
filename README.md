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
- Buscar detalhada por nome ou id de usuários (sem mostrar a senha)
- Busca detalhada por nome ou id de categorias
- Busca por id de transações
- Remoção de usuários
- Retorno de erro ao tentar cadastrar um usuário com e-mail já existente

---

## Estrutura Básica das Tabelas

- **Usuário**: id, nome, email, senha  
- **Categoria**: id, nome, descrição, usuario_id
- **Transação**: id, descrição, valor, data, categoria_id, usuario_id, tipo (receita/despesa)  

---

## 📌 Próximos Passos

- Autenticação e login (JWT)
- Filtros por data nas transações
- Remoção de transações e categorias

---
  
