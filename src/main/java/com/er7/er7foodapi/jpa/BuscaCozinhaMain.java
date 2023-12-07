package com.er7.er7foodapi.jpa;

import com.er7.er7foodapi.Er7foodApiApplication;
import com.er7.er7foodapi.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class BuscaCozinhaMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext  = new SpringApplicationBuilder(Er7foodApiApplication.class)
            .web(WebApplicationType.NONE)
                .run(args);

        CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);

        Cozinha cozinha = cadastroCozinha.buscar(1L);

        System.out.printf("\n%d -- %s\n\n", cozinha.getId(), cozinha.getNome());
    }
}
