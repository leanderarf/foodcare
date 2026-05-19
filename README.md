# Food Care 🍎
> Sistema inteligente de intermediação para combate ao desperdício de alimentos.

## 📌 Sobre o Projeto
O **Food Care** é uma plataforma desenvolvida como parte do Projeto de Extensão da UNIP. O objetivo principal é conectar doadores de alimentos (supermercados, restaurantes, produtores) a instituições receptoras (ONGs, abrigos), facilitando a gestão de doações e evitando que alimentos próprios para consumo sejam descartados.

## 🛠️ Tecnologias Utilizadas
- **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate.
- **Frontend:** React.js, Vite, Axios, React Router.
- **Banco de Dados:** MySQL.
- **Documentação:** Swagger (OpenAPI 3.0).

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK 17
- Node.js (v18+)
- MySQL Server

### 1. Configurando o Backend
1. Navegue até a pasta do servidor.
2. Configure o arquivo `application.properties` com as credenciais do seu banco de dados local.
3. Execute o comando:
   ```bash
   mvn spring-boot:run
   ```
### 2. Configurando o Frontend
1. Navegue até a pasta web.
2. Instale as dependências: 
  ```bash
  npm install
  ```
4. Inicie a aplicação
  ```bash
  npm rund dev
  ```

### 📖 Documentação da API (Swagger)
Com o backend em execução, a documentação detalhada dos endpoints pode ser acessada em:
http://localhost:8080/swagger-ui.html

### 👥 Fluxo de Usuários
Doador: Cadastra alimentos, gerencia estoque e aprova/recusa solicitações.

Receptor: Visualiza alimentos disponíveis em tempo real e realiza pedidos baseados na necessidade e validade.
