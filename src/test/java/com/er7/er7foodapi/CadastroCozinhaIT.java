package com.er7.er7foodapi;

import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {


	@LocalServerPort private int port;
	@Autowired DatabaseCleaner databaseCleaner;
	@Autowired CozinhaRepository cozinhaRepository;

	// cenário
	// ação
	// validação
	// givenPreCondicao_whenEstadoEmTeste_thenComportamentoEsperado

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		preparaDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(2))
			.body("nome", Matchers.hasItems("Tailandesa", "Americana"));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
			.body("{ \"nome\": \"Chinesa\" }")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultaCozinhaExistente() {
		given()
			.pathParams("cozinhaID", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaID}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo("Americana"));
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultaCozinhaInexistente() {
		given()
			.pathParams("cozinhaID", 100)
			.accept(ContentType.JSON)
			.when()
			.get("/{cozinhaID}")
			.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void preparaDados() {
		var cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		var cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
	}
}



	/*@Test
	void deveAtribuirID_QuandoCadastrarCozinhaComDadosCorretos() {

		var novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		novaCozinha = cozinhaService.salvar(novaCozinha);

		assertThat(novaCozinha.getId()).isNotNull();
		assertThat(novaCozinha.getNome()).isNotBlank();
	}

	@Test
	void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		var novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado = assertThrows(ConstraintViolationException.class, () -> {
			cozinhaService.salvar(novaCozinha);
		});
		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		EntidadeEmUsoException erroEsperado =
			assertThrows(
				EntidadeEmUsoException.class,
				() -> cozinhaService.excluir(1L));
		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		CozinhaNaoEncontradaException erroEsperado =
			assertThrows(
				CozinhaNaoEncontradaException.class,
				() -> cozinhaService.excluir(1000L));
		assertThat(erroEsperado).isNotNull();
	}*/