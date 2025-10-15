
# 💸 POC de PIX Recorrente (Mensalidades e/ou Assinaturas)  
# 💸 POC of Recurring PIX (Monthly Payments and/or Subscriptions)

<div align="center">
  <h3>API para agendamentos de envio de PIX recorrentes com controle antifraude<br>
  API for scheduling recurring PIX transfers with antifraud control</h3>
</div>

---

## 🇧🇷 **Descrição**
Esta é uma prova de conceito (**POC**) de uma **API** para o **agendamento de envios recorrentes de PIX**, ideal para mensalidades, assinaturas ou pagamentos periódicos.  
A API inclui um **módulo antifraude**, documentação automática via **Swagger**, e **geração automática de banco de dados e migrations** com **Flyway**.

---

## 🇺🇸 **Description**
This is a proof of concept (**POC**) for an **API** that schedules **recurring PIX transfers**, designed for subscriptions, monthly fees, or periodic payments.  
It includes an **antifraud module**, **Swagger documentation**, and **automatic database creation and migration management** using **Flyway**.

---

## ⚙️ **Features**
### 🇧🇷 Português
- Endpoints para **agendamento** e **log de agendamentos**;  
- **Controle antifraude** integrado;  
- **Documentação da API** via Swagger;  
- **Criação e atualização automática do banco de dados**.

### 🇺🇸 English
- Endpoints for **scheduling** and **schedule logs**;  
- Integrated **antifraud control**;  
- **API documentation** with Swagger;  
- **Automatic database creation and updates**.

---

## 🛡️ **Controle Antifraude / Antifraud Control**

### 🇧🇷 Português
O controle antifraude é executado sempre que um novo agendamento é criado ou atualizado.  
Atualmente, ele realiza três verificações principais:

1. **Duplicidade de agendamento** — impede múltiplos agendamentos para o mesmo destinatário;  
2. **Limite de valor** — evita transferências acima de um valor máximo configurado;  
3. **Limite de agendamentos** — restringe o número de agendamentos para a mesma chave PIX de destino.

### 🇺🇸 English
The antifraud control runs every time a schedule is created or updated.  
Currently, it performs three main checks:

1. **Duplicate scheduling** — prevents multiple schedules for the same recipient;  
2. **Value limit** — blocks transfers exceeding a predefined maximum amount;  
3. **Scheduling limit** — restricts the number of schedules for the same PIX destination key.

---

## 🧠 **Como Funciona / How It Works**

### 🇧🇷 Português
A API está pronta para uso assim que for executada, **sem necessidade de scripts adicionais**.  
Ao criar um agendamento:
- O **módulo antifraude** é automaticamente executado;  
- Caso alguma inconsistência seja detectada, o **status do agendamento** é atualizado e um **log é registrado no banco de dados**.

### 🇺🇸 English
The API is **ready to use upon startup**, with **no additional scripts required**.  
When a new schedule is created:
- The **antifraud module** runs automatically;  
- If any issue is detected, the **schedule status** is updated and a **log entry** is saved in the database.

---

## 📚 **Recursos Importantes / Important Resources**

### 🇧🇷 Português
- A documentação da API é gerada automaticamente com **Swagger** via a dependência  
  `springdoc-openapi-starter-webmvc-ui`.  
- O banco de dados é **SQLite**, criado automaticamente ao iniciar a API.  
- As **migrations** e o **controle de versão** do banco são gerenciados pelo **Flyway**.

### 🇺🇸 English
- API documentation is automatically generated with **Swagger** using  
  `springdoc-openapi-starter-webmvc-ui`.  
- The database used is **SQLite**, created automatically when the API starts.  
- **Migrations** and **version control** of the database are handled by **Flyway**.

---

## 🧩 **Tecnologias Principais / Main Technologies**
- **Java / Spring Boot**
- **Springdoc OpenAPI (Swagger UI)**
- **SQLite Database**
- **Flyway Core**
- **Antifraud Engine (Custom Module)**

---

## 🚀 **Execução / Running the Project**

### 🇧🇷 Português
1. Clone o repositório:  
   ```bash
   git clone https://github.com/samuelRibeiro91/poc-pix-recorrente.git
   cd poc-pix-recorrente
   ```
2. Compile e execute:  
   ```bash
   mvn spring-boot:run
   ```
3. Acesse o Swagger em:  
   ```
   http://localhost:8080/swagger-ui.html
   ```

### 🇺🇸 English
1. Clone the repository:  
   ```bash
   git clone https://github.com/samuelRibeiro91/poc-pix-recorrente.git
   cd poc-pix-recorrente
   ```
2. Build and run:  
   ```bash
   mvn spring-boot:run
   ```
3. Access Swagger documentation at:  
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## 🧾 **Licença / License**
MIT License © 2025  
