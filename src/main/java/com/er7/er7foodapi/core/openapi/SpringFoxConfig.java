package com.er7.er7foodapi.core.openapi;

import com.er7.er7foodapi.api.controller.openapi.model.PageableModelOpenApi;
import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();
        return new Docket(DocumentationType.OAS_30)
            .select()
            .apis(
                RequestHandlerSelectors.basePackage("com.er7.er7foodapi.api"))
            .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostAndPutResponseMessages())
                .globalResponses(HttpMethod.PUT,globalPostAndPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .apiInfo(apiInfo())
                .tags(
                    new Tag("Cidades", "Gerencia as cidades."),
                    new Tag("Grupos", "Gerencia os grupos de usuários")
                );
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    private List<Response> globalPostAndPutResponseMessages() {
        return Arrays.asList(
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .description("Requisição inválida (erro do cliente).")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelRefenrece())
                .build(),
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .description("Erro interno do servidor")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelRefenrece())
                .build(),
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .build(),
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                .description("Requisição recusada porque o corpo está em um formato não suportado")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelRefenrece())
                .build()
        );
    }

    private Consumer<RepresentationBuilder> getProblemaModelRefenrece() {
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(ref -> ref.key((k -> k.qualifiedModelName(q -> q.name("Problema").namespace("com.er7.er7foodapi.api.exceptionhandler")))))
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .description("Requisição inválida (erro do cliente).")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelRefenrece())
                .build(),
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .description("Erro interno do servidor")
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelRefenrece())
                .build()
        );
    }

    private List<Response> globalGetResponseMessages(){
        return Arrays.asList(
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .description("Consulta realizada com sucesso.")
                .build(),
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .representation(MediaType.APPLICATION_JSON)
                .apply(getProblemaModelRefenrece())
                .description("Erro interno do servidor")
                .build(),
            new ResponseBuilder()
                .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor.")
                .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("ER7Food API")
            .description("API aberta para clientes e restaurantes.")
            .version("1")
            .contact(new Contact("ER7FoodAPI", "www.er7food.com", "carlos.er7@gmail.com"))
            .build();
    }
}
