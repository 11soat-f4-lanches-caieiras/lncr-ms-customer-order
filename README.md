# lncr-ms-customer-order

## Descrição

Microserviço responsável pelo gerenciamento de **Pedidos de Clientes** no sistema Lanches Caieiras. Este serviço implementa as funcionalidades relacionadas ao ciclo de vida dos pedidos, incluindo criação, atualização de status, consulta e acompanhamento de pedidos.

## Funcionalidades

### Endpoints Disponíveis (`/customerOrders`)

| Método | Path | Descrição |
|--------|------|-----------|
| `POST` | `/customerOrders` | Criar novo pedido |
| `GET` | `/customerOrders/{customerOrderId}` | Buscar pedido por ID |
| `GET` | `/customerOrders/status/{statusList}` | Buscar pedidos por lista de status |
| `PATCH` | `/customerOrders/{customerOrderId}/status/{newStatus}` | Atualizar status do pedido |

**Parâmetros de consulta:**
- `includeFoodItems`: Incluir itens de alimentação na resposta (padrão: false)
- `forceUpdate`: Forçar atualização de status (padrão: false)

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.5
- PostgreSQL
- Maven
- Cucumber (BDD)
- JUnit 5

## Sonar Quality Gate

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=11soat-f4-lanches-caieiras_lncr-ms-customer-order&metric=alert_status&token=ef14442d80b6b8929b2cd0046280743d5ff20de9)](https://sonarcloud.io/summary/new_code?id=11soat-f4-lanches-caieiras_lncr-ms-customer-order)

Acesse o dashboard completo: [SonarCloud - lncr-ms-customer-order](https://sonarcloud.io/project/overview?id=11soat-f4-lanches-caieiras_lncr-ms-customer-order)

## Dependências

- **lncr-core** (versão 3.0) - Biblioteca com regras de negócio e entidades de domínio
- **lncr-commons** (versão 1.0) - Biblioteca comum compartilhada com configurações e utilitários

## Guia de Download e Execução

### Pré-requisitos

- **Java 21** instalado
- **Maven 3.8+** instalado
- **PostgreSQL 13+** em execução
- **Git** instalado

### Configuração do Banco de Dados

```sql
-- Criar database
CREATE DATABASE lncr_customer_order;

-- Criar usuário (opcional)
CREATE USER lncr_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE lncr_customer_order TO lncr_user;
```

### Variáveis de Ambiente

Crie um arquivo `.env` ou configure as seguintes variáveis de ambiente:

```bash
# Configuração do Servidor
SERVER_PORT=8080

# PostgreSQL
POSTGRES_URL=jdbc:postgresql://localhost:5432/lncr_customer_order
POSTGRES_USER=lncr_user
POSTGRES_PASSWORD=your_password

# URLs da Aplicação
LNCR_INTERNAL_URL=http://localhost:8080
LNCR_EXTERNAL_URL=http://localhost:8080
```

### Download e Instalação

```bash
# Clone o repositório
git clone https://github.com/11soat-f4-lanches-caieiras/lncr-ms-customer-order.git

# Entre no diretório do projeto
cd lncr-ms-customer-order/customerorder

# Configure o GitHub Packages (necessário para dependências lncr-core e lncr-commons)
# Crie o arquivo ~/.m2/settings.xml com suas credenciais do GitHub

# Compile o projeto
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

### Executando com Docker

```bash
# Build da imagem
docker build -t lncr-ms-customer-order:latest .

# Execute o container
docker run -p 8080:8080 \
  -e POSTGRES_URL=jdbc:postgresql://host.docker.internal:5432/lncr_customer_order \
  -e POSTGRES_USER=lncr_user \
  -e POSTGRES_PASSWORD=your_password \
  -e LNCR_INTERNAL_URL=http://localhost:8080 \
  -e LNCR_EXTERNAL_URL=http://localhost:8080 \
  lncr-ms-customer-order:latest
```

### Executando os Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com cobertura
mvn test -Pcoverage

# Executar apenas testes BDD
mvn test -Dcucumber.filter.tags="@bdd"
```

### Verificando a Aplicação

Após iniciar a aplicação, acesse:

- **Health Check**: http://localhost:8080/actuator/health
- **API Base**: http://localhost:8080/customerOrders
