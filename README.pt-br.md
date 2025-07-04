# 💸 Projeto de Finanças Pessoais

Backend para um sistema simples de **controle financeiro pessoal**, com funcionalidades de cadastro e consulta de usuários, categorias e transações.

---

## 🚀 Tecnologias Utilizadas

- **Java 21 (OpenJDK)**
- **Spring Boot**
- **PostgreSQL**
- **Docker**
- **Postman** (para testes de API)

---

## ✅ Funcionalidades Atuais

- ✅ Cadastro de usuários, categorias e transações  
- ✅ Consulta de usuários por nome ou ID (sem exibir a senha)  
- ✅ Consulta de categorias por nome ou ID  
- ✅ Consulta de transações por ID  
- ✅ Remoção de usuários  
- ✅ Validação de campos obrigatórios (`@Valid`)  
- ✅ Prevenção de duplicidade de e-mails (erro ao cadastrar com e-mail já existente)  
- ✅ Geração de **PDF com as transações e valores gastos** de um usuário
- ✅ Criadas variáveis locais para segurança: senha e usuário (para banco de dados e email).

---

## 🗃️ Estrutura das Tabelas

### Usuário
- `id`
- `nome`
- `email`
- `senha`

### Categoria
- `id`
- `nome`
- `descrição`
- `usuario_id`

### Transação
- `id`
- `descrição`
- `valor`
- `data`
- `categoria_id`
- `usuario_id`
- `tipo`
---

## ✉️ Contato

Fique à vontade para entrar em contato comigo pelo [LinkedIn](https://www.linkedin.com/in/pedrohjacinto) caso tenha dúvidas, sugestões ou queira trocar uma ideia sobre o projeto!
