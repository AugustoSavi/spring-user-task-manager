# Cadastro de Usuários e Tarefas - Aplicação Spring Boot

Bem-vindo à aplicação de Cadastro de Usuários e Tarefas desenvolvida em Spring Boot. Esta aplicação permite que você gerencie usuários e suas tarefas associadas.

## Instruções de Uso

### Inicialização

Antes de começar a usar a aplicação, siga estas etapas para inicializar o servidor Spring Boot:

1. Certifique-se de que você tenha o Maven instalado na sua máquina. Se não, você pode instalá-lo a partir do site oficial do Maven: [Apache Maven](https://maven.apache.org/download.cgi).

2. Clone o repositório deste projeto:

   ```
   git clone git@github.com:AugustoSavi/spring-user-task-manager.git
   ```

3. Navegue até a pasta do projeto:

   ```
   cd spring-user-task-manager
   ```

4. Inicie o servidor Spring Boot com o seguinte comando:

   ```
   mvn spring-boot:run
   ```

   Isso iniciará o servidor na porta padrão 8080.

### Banco de Dados H2 Console

A aplicação utiliza um banco de dados H2 em memória para armazenar os dados. Você pode acessar o console do H2 para visualizar e gerenciar o banco de dados. Use as seguintes informações de conexão:

- URL do Console H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:todolist`
- Driver Class: `org.h2.Driver`
- Username: `admin`
- Senha: `admin`

Certifique-se de que o servidor esteja em execução antes de tentar acessar o console H2.

### Documentação do Swagger

Esta aplicação é documentada usando o Swagger, que fornece uma interface interativa para explorar os endpoints da API. Você pode acessar a documentação do Swagger no seguinte URL:

- Documentação do Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

A documentação do Swagger oferece informações detalhadas sobre os endpoints da API, parâmetros e exemplos de solicitação. É uma ferramenta útil para explorar e testar os recursos da aplicação.

## Contribuindo

Sinta-se à vontade para contribuir para este projeto. Se você encontrar problemas ou tiver sugestões de melhorias, por favor, abra uma [issue](https://github.com/AugustoSavi/spring-user-task-manager/issues) ou envie um [pull request](https://github.com/AugustoSavi/spring-user-task-manager/pulls).

## Licença

Este projeto é licenciado sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para obter mais detalhes.
