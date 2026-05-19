package br.com.food_care.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FoodCare API - Sistema de Gerenciamento de Doações de Alimentos")
                        .version("1.0.0")
                        .description("API desenvolvida para o Projeto de Extensão Universitária FoodCare. " +
                                "Gerencia o cadastro de doadores, receptores, estoque de alimentos e o " +
                                "fluxo de aprovação de solicitações, combatendo o desperdício alimentar.")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento FoodCare")
                                .email("suporte@foodcare.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
