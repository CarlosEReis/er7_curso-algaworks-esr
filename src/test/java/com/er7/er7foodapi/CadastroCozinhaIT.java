package com.er7.er7foodapi;

import com.er7.er7foodapi.domain.exception.CozinhaNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cozinhaService;

	// cenário
	// ação
	// validação

	// givenPreCondicao_whenEstadoEmTeste_thenComportamentoEsperado

	@Test
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
	}
}
