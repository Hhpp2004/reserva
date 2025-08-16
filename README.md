# Sistema de Reserva de Mesas

Este projeto é uma API desenvolvida em Java utilizando Spring Boot e Spring Security para gerenciar reservas de mesas em estabelecimentos. O sistema permite o cadastro de usuários, gerenciamento de mesas, realização de reservas, autenticação e controle de acesso usando o Jwt e oauth 2.0.

## Funcionalidades

- Cadastro e autenticação de usuários
- Gerenciamento de mesas (criação, atualização, exclusão)
- Realização e cancelamento de reservas
- Controle de capacidade e status das mesas
- Tratamento de exceções personalizadas

## Estrutura do Projeto

```
src/main/java/backend/system/reserva/
├── Config/           # Configurações de segurança e administração
├── Controller/       # Controllers REST para mesas, reservas e usuários
├── DTO/              # Data Transfer Objects
├── Exception/        # Exceções personalizadas
├── Model/            # Modelos de entidades
├── Repository/       # Repositórios JPA
├── Service/          # Lógica de negócio
```

## Como executar

1. Certifique-se de ter o Java 17+ e o Maven instalados.
2. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   ```
3. Instale as dependências e execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Acesse a API via `http://localhost:8080`

## Configurações

- As chaves de autenticação estão em `src/main/resources/app.key` e `app.pub`.
- Configurações adicionais podem ser feitas em `application.properties`.

## Testes

Os testes estão localizados em `src/test/java/backend/system/reserva/`.
Para executar:

```bash
./mvnw test
```

## Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
- JPA/Hibernate

## Autor

Projeto desenvolvido por Henrique (Hhpp2004).

---

Sinta-se à vontade para contribuir ou sugerir melhorias!
