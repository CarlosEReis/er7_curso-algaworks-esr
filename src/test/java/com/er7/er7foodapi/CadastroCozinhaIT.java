package com.er7.er7foodapi;

import com.er7.er7foodapi.domain.service.CadastroCozinhaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

	@LocalServerPort
	private int port;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	// cenário
	// ação
	// validação
	// givenPreCondicao_whenEstadoEmTeste_thenComportamentoEsperado

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
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