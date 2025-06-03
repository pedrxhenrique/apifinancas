# 💸 Personal Finance Project

A simple backend system for **personal financial management**, with user, category, and transaction registration and querying.

> 🇧🇷 [Read this documentation in Portuguese](README.pt-br.md)

---

## 🚀 Technologies Used

- **Java 21 (OpenJDK)**
- **Spring Boot**
- **PostgreSQL**
- **Docker**
- **Postman** (for API testing)

---

## ✅ Current Features

- ✅ Register users, categories, and transactions  
- ✅ Query users by name or ID (password is hidden)  
- ✅ Query categories by name or ID  
- ✅ Query transactions by ID  
- ✅ Delete users  
- ✅ Field validation with `@Valid` (no null values allowed)  
- ✅ Error returned if trying to register a user with an existing email  
- ✅ Generate **PDF report** of user transactions and total spent  

---

## 🗃️ Database Schema Overview

### User
- `id`
- `name`
- `email`
- `password`

### Category
- `id`
- `name`
- `description`
- `user_id`

### Transaction
- `id`
- `description`
- `amount`
- `date`
- `category_id`
- `user_id`
- `type`

---

## ✉️ Contact

Feel free to connect with me on [LinkedIn](https://www.linkedin.com/in/pedrohjacinto) if you have any questions, suggestions, or just want to talk about the project!
