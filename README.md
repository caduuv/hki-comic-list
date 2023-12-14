# HKI - Aplicação de Listagem de Histórias em Quadrinhos

## Visão Geral

Bem-vindo à HKI, uma aplicação desenvolvida em Spring Boot e Java 17 que permite a listagem de histórias em quadrinhos. Esta aplicação utiliza um banco de dados PostgreSQL para armazenar informações sobre as histórias em quadrinhos, como título, autor, ano de lançamento, etc.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina antes de executar a aplicação:

- Java 17
- Spring Boot
- PostgreSQL

## Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL chamado `hki_database`.
2. Abra o arquivo `application.properties` e configure as propriedades `spring.datasource.username` e `spring.datasource.password` de acordo com as credenciais do seu banco de dados.

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/hki_database
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha```

##Executando a Aplicação

1.Clone este repositório.
2.Navegue até o diretório da aplicação.
 ```bash
cd hki```
3.Execute o seguinte comando para construir e iniciar a aplicação.

 ```bash
./mvnw spring-boot:run```
