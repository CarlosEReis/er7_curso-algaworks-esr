package com.er7.er7foodapi.core.openapi;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.CozinhaModel;
import com.er7.er7foodapi.api.model.PedidoResumoModel;
import com.er7.er7foodapi.api.openapi.model.CozinhasModelOpenApi;
import com.er7.er7foodapi.api.openapi.model.PageableModelOpenApi;
import com.er7.er7foodapi.api.openapi.model.PedidoResumoModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
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
//                .globalRequestParameters(Collections.singletonList(
//                    new RequestParameterBuilder()
//                        .name("campos")
//                        .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//                        .in(ParameterType.QUERY)
//                        .required(true)
//                        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                        .build()
//                ))
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class,
                        File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                    typeResolver.resolve(Page.class, CozinhaModel.class),
                    CozinhasModelOpenApi.class
                ))
                .alternateTypeRules(AlternateTypeRules.newRule(
                    typeResolver.resolve(Page.class, PedidoResumoModel.class),
                    PedidoResumoModelOpenApi.class
                ))
                .apiInfo(apiInfo())
                .tags(
                    new Tag("Cidades", "Gerencia as cidades."),
                    new Tag("Grupos", "Gerencia os grupos de usuários."),
                    new Tag("Cozinhas", "Genrencia as cozinhas."),
                    new Tag("Formas de pagamento", "Gerencia as formas de pagamento."),
                    new Tag("Pedidos", "Gerencia os pedidos."),
                    new Tag("Restaurantes", "Gerencia os restaurantes."),
                    new Tag("Estados", "Gerencia os estados"),
                    new Tag("Produtos", "Gerencia os produtos de um restaurante."),
                    new Tag("Usuários", "Gerencia os usuários"),
                    new Tag("Estatísticas", "Estatísticas da AlgaFood")
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
