package com.er7.er7foodapi;

import com.er7.er7foodapi.api.exceptionhandler.ProblemType;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import com.er7.er7foodapi.util.DatabaseCleaner;
import com.er7.er7foodapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    @LocalServerPort private int port;
    @Autowired DatabaseCleaner databaseCleaner;

    @Autowired private CozinhaRepository cozinhaRepository;
    @Autowired private RestauranteRepository restauranteRepository;

    private static final long ID_RESTAURANTE_INEXISTENTE = 100L;
    private static final String VIOLACAO_REGRA_NEGOCIO_PROBLEM_TYPE = ProblemType.ERRO_NEGOCIO.getTitle();
    private static final String DADOS_INVALIDOS_PROBLEM_TYPE = ProblemType.DADOS_INVALIDOS.getTitle();

    private Restaurante burgerTopRestaurante;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteCozinhaInexistente;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        jsonRestauranteCorreto = ResourceUtils.getContentFromResource("/json/correto/restaurante-new-york-barbecue.json");
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");
        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");
        jsonRestauranteCozinhaInexistente = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

        databaseCleaner.clearTables();
        preparaDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
            .body(jsonRestauranteCorreto)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
            .body(jsonRestauranteSemFrete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
            .body(jsonRestauranteSemCozinha)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
            .body(jsonRestauranteCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(VIOLACAO_REGRA_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultaRestauranteInexistente() {
        given()
            .pathParams("restauranteID", ID_RESTAURANTE_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteID}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
            .pathParams("restauranteID", burgerTopRestaurante.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteID}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(burgerTopRestaurante.getNome()))
        ;
    }

    private void preparaDados() {
        var cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        var cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);

        var comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    }
}
