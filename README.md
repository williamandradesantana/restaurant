# 🍽️ Restaurant API

API REST para gestão completa das operações de um restaurante: pedidos, cozinha, produtos, mesas, fechamento de conta e pagamento.

## ✨ Funcionalidades

- **Produtos e categorias**: cadastro, atualização, listagem e remoção de produtos.
- **Pedidos**: criação de pedidos, adição de itens e listagem paginada.
- **Cozinha (Kitchen)**: fila de itens pendentes e em preparo, com transições de status (`enviar item`, `iniciar preparo`, `marcar como pronto`).
- **Monitoramento da cozinha em tempo real**: worker assíncrono (`KitchenWorker`), executado a cada minuto com Virtual Threads, que verifica itens com tempo de preparo estourado e dispara alertas.
- **Fechamento de conta**: cálculo automático de subtotal, taxa de serviço e descontos, com validações de regras de negócio (ex.: não é possível fechar uma conta com itens ainda não entregues).
- **Pagamento**: integração com um serviço externo de pagamento via `Feign Client`.
- **Tratamento de erros centralizado** com respostas padronizadas (`GlobalExceptionHandler`).
- **Documentação interativa** da API via OpenAPI/Swagger.

## 🛠️ Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Cloud OpenFeign
- PostgreSQL
- Flyway (versionamento de banco de dados)
- Spring Cache (Caffeine)
- springdoc-openapi (Swagger UI)
- Docker / Docker Compose
- Maven

## 📐 Arquitetura

O projeto segue uma separação clássica em camadas:

```
controllers  → exposição dos endpoints REST
services     → regras de negócio
repositories → acesso a dados (Spring Data JPA)
domain       → entidades e enums do domínio
dtos         → objetos de request/response
client       → integração com serviços externos (Feign)
worker       → processos assíncronos/agendados
exception    → tratamento de erros
```

## 🚀 Como executar

### Pré-requisitos

- Java 21+
- Maven (ou use o wrapper `./mvnw` incluso no projeto)
- Docker e Docker Compose

### 1. Clone o repositório

```bash
git clone https://github.com/williamandradesantana/restaurant.git
cd restaurant
```

### 2. Configure as variáveis de ambiente

Copie o arquivo de exemplo e preencha com os seus valores:

```bash
cp .env.example .env
```

Variáveis necessárias:

| Variável | Descrição |
|---|---|
| `POSTGRES_DB` | Nome do banco de dados |
| `POSTGRES_USER` | Usuário do banco |
| `POSTGRES_PASSWORD` | Senha do banco |
| `POSTGRES_HOST` | Host do banco (ex.: `localhost`) |
| `POSTGRES_PORT` | Porta do banco (ex.: `5432`) |

### 3. Suba o banco de dados

```bash
docker compose up -d
```

### 4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

> As migrações do banco (Flyway) são executadas automaticamente na inicialização.

### 5. Documentação da API

Com a aplicação rodando, a documentação Swagger fica disponível em:

```
http://localhost:8080/swagger-ui.html
```

## 📍 Principais endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/products` | Lista produtos |
| `POST` | `/api/products` | Cria produto |
| `PUT` | `/api/products/{id}` | Atualiza produto |
| `DELETE` | `/api/products/{id}` | Remove produto |
| `GET` | `/api/orders` | Lista pedidos (paginado) |
| `POST` | `/api/orders` | Cria pedido |
| `GET` | `/api/orders/{orderId}/items` | Lista itens do pedido |
| `POST` | `/api/orders/{orderId}/items` | Adiciona item ao pedido |
| `POST` | `/api/orders/{orderId}/pay` | Processa pagamento do pedido |
| `GET` | `/api/kitchen/pending-items` | Lista itens pendentes na cozinha |
| `GET` | `/api/kitchen/items-in-preparation` | Lista itens em preparo |
| `PATCH` | `/api/kitchen/items/{itemId}/send-item` | Envia item para a cozinha |
| `PATCH` | `/api/kitchen/items/{itemId}/begin-preparation` | Inicia o preparo do item |
| `PATCH` | `/api/kitchen/items/{itemId}/mark-as-ready` | Marca item como pronto |
| `POST` | `/api/orders/{orderId}/closing` | Fecha a conta do pedido |

## 🧪 Testes

```bash
./mvnw test
```

## 👤 Autor

**William Batista Andrade Santana**
