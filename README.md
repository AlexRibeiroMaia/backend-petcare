# backend-petcare

Backend do aplicativo mobile PETCARE (Spring Boot + PostgreSQL/Oracle + RabbitMQ + JWT).

## Requisitos

- Java 21
- Docker e Docker Compose (para PostgreSQL, RabbitMQ e pgAdmin locais)
- Git Bash/Zsh/Bash (terminal)

## Instalacao e configuracao

1. Copie o arquivo de ambiente:
   - `cp .env.example .env`
2. Edite o arquivo `.env` com as variaveis obrigatorias:
   - Banco: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `DB_DRIVER_CLASS_NAME`
   - JWT: `JWT_SECRET` (minimo de 32 caracteres), `JWT_EXPIRATION_MINUTES`
   - RabbitMQ/Email: variaveis `RABBITMQ_*`, `AWS_*`, `SES_FROM_EMAIL`
   - Infra Docker: `POSTGRES_*`, `PGADMIN_*`

### Exemplo de `.env` para rodar com PostgreSQL local (Docker)

```env
SPRING_PROFILES_ACTIVE=dev

DB_URL=jdbc:postgresql://localhost:5432/petcare
DB_USERNAME=petcare
DB_PASSWORD=petcare
DB_DRIVER_CLASS_NAME=org.postgresql.Driver

JWT_SECRET=troque-por-uma-chave-com-pelo-menos-32-caracteres
JWT_EXPIRATION_MINUTES=60

POSTGRES_DB=petcare
POSTGRES_USER=petcare
POSTGRES_PASSWORD=petcare
POSTGRES_PORT=5432

PGADMIN_DEFAULT_EMAIL=admin@petcare.com
PGADMIN_DEFAULT_PASSWORD=admin
PGADMIN_PORT=5050

AWS_REGION=us-east-1
AWS_ACCESS_KEY_ID=dummy
AWS_SECRET_ACCESS_KEY=dummy
AWS_SESSION_TOKEN=
AWS_SES_ENDPOINT=https://email.us-east-1.amazonaws.com
SES_FROM_EMAIL=no-reply@petcare.com
```

## Execucao da aplicacao

1. Suba a infraestrutura local:
   - `docker compose up -d postgres rabbitmq pgadmin`
2. Verifique se os containers estao saudaveis:
   - `docker compose ps`
3. Suba o backend:
   - `./mvnw spring-boot:run`

Opcional:
- Rodar testes: `./mvnw test`

## Acesso a aplicacao

- API backend: `http://localhost:8080`
- Endpoint publico de login: `POST http://localhost:8080/auth/login`
- Endpoints protegidos: enviar `Authorization: Bearer <token>`
- RabbitMQ Management: `http://localhost:15672` (user: `admin`, senha: `admin`, vhost: `PetCareMQ`)
- pgAdmin: `http://localhost:${PGADMIN_PORT}` (valor definido no `.env`)
- PostgreSQL: `localhost:${POSTGRES_PORT}` (valor definido no `.env`)

Exemplo de login:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"usuario@teste.com","password":"123456"}'
```

## Perfis de execucao

- `dev` (padrao): `ddl-auto=update`, Flyway desabilitado
- `homolog`: `ddl-auto=none`
- `prod`: `ddl-auto=none`

Para trocar perfil:

```bash
SPRING_PROFILES_ACTIVE=homolog ./mvnw spring-boot:run
```

## Testar com Expo no celular fisico

1. Deixe computador e celular na mesma rede Wi-Fi.
2. Descubra o IP local do computador:
   - macOS: `ipconfig getifaddr en0`
3. No app Expo (projeto mobile), configure:
   - `EXPO_PUBLIC_API_URL=http://SEU_IP_LOCAL:8080`
4. Inicie o Expo:
   - `npx expo start --lan`
5. Escaneie o QR Code no Expo Go.

Observacoes:
- No celular, nao use `localhost`; use o IP da maquina.
- CORS ja esta preparado para localhost e rede LAN via `APP_CORS_ALLOWED_ORIGIN_PATTERNS`.
- Em app nativo (Expo Go), CORS normalmente nao bloqueia; e mais relevante para `expo web`.

## Troubleshooting rapido

- Erro `JWT secret must be at least 32 bytes`:
  - Ajuste `JWT_SECRET` para 32+ caracteres.
- Erro de conexao com banco:
  - Confirme `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e `DB_DRIVER_CLASS_NAME`.
  - Se usar Docker local, confirme `docker compose ps` com `postgres` saudavel.
- Erro de conexao com RabbitMQ:
  - Confirme se `rabbitmq` esta ativo e se o vhost `PetCareMQ` esta sendo usado.
