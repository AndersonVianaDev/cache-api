# 📦 User Registration API with Redis Cache and Email Verification

Uma aplicação backend desenvolvida em **Java 21** com **Spring Boot 3.5.0**, focada na prática de **cache com Redis** e **envio de e-mails com código de segurança**.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.0
- Spring Security
- Spring Data JPA
- Spring Java Mail
- Redis
- PostgreSQL
- Lombok

---

## 📚 Sobre o Projeto

A API implementa um registro de usuário básico com funcionalidade adicional para **recuperação de senha via e-mail**, utilizando **Redis como cache temporário**.

### 🔐 Funcionalidades:

- Registro de usuários.
- Envio de **código de verificação** para o e-mail do usuário.
- Armazenamento do código em cache (Redis) por **15 minutos**.
- Endpoint para redefinição de senha com validação do código.

---

## 🧩 Como Funciona a Recuperação de Senha

1. O usuário envia o e-mail para o endpoint de **send-reset-code**.
2. A API gera um código numérico aleatório (ex: `562349`).
3. Esse código é:
    - Enviado para o e-mail do usuário.
    - Armazenado no Redis com a **chave igual ao e-mail** e **expiração de 15 minutos**.
4. O usuário recebe o código e o envia para o endpoint de **reset-password**, junto com a nova senha.
5. A API valida:
    - Se o código ainda existe no Redis.
    - Se o código enviado é igual ao código armazenado.
6. Se válido:
    - A senha do usuário é atualizada.
    - O código é removido do cache.
7. Se inválido ou expirado:
    - É retornada uma mensagem informando erro: `Código inválido ou expirado`.

---

## 📫 Exemplo de Requisições

### Enviar Código para o E-mail
```http
POST /users/send-reset-code
Content-Type: application/json

{
  "email": "usuario@email.com"
}

POST /users/reset-password
Content-Type: application/json

{
    "email": "usuario@email.com",
    "code": "4566544",
    "newPassword": "new password"
}
