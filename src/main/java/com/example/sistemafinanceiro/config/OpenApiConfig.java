package com.example.sistemafinanceiro.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Ajuda Financeira — API")
                        .description("""
                                API REST para gerenciamento de finanças pessoais.
                                Permite cadastrar usuários, registrar despesas e ganhos,
                                além de consultar relatórios mensais e por período.
                                
                                **Base URL:** `http://localhost:8080/api`
                                
                                **Autenticação:** nenhuma nesta versão (futura integração JWT).
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Rafael Novo da Rosa")
                                .email("rafael@example.com")
                                .url("https://github.com/ProjetoFinalMaisPraTI/Back-End"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Guia de testes com Postman")
                        .url("https://github.com/ProjetoFinalMaisPraTI/Back-End/blob/main/README-postman.md"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/api")
                                .description("Ambiente local de desenvolvimento")))
                .tags(List.of(
                        new Tag()
                                .name("Usuários")
                                .description("Cadastro e gerenciamento de usuários do sistema"),
                        new Tag()
                                .name("Despesas")
                                .description("Registro e consulta de despesas por usuário, mês e período"),
                        new Tag()
                                .name("Ganhos")
                                .description("Registro e consulta de ganhos por usuário, mês e período")));
    }
}
