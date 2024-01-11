package com.er7.er7foodapi;

import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cozinhaService;

	// cenário
	// ação
	// validação

	@Test
	void testaCadastroCozinhaComSucesso() {

		var novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		novaCozinha = cozinhaService.salvar(novaCozinha);

		assertThat(novaCozinha.getId()).isNotNull();
		assertThat(novaCozinha.getNome()).isNotBlank();
	}

	@Test
	void testaCadastroCozinhaSemNome() {
		var novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado = assertThrows(ConstraintViolationException.class, () -> {
			cozinhaService.salvar(novaCozinha);
		});
		assertThat(erroEsperado).isNull();
	}
}
