package com.er7.er7foodapi.jpa;

import com.er7.er7foodapi.Er7foodApiApplication;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import com.er7.er7foodapi.infrastructure.repository.RestauranteRepositoryImpl;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaRestauranteMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext  = new SpringApplicationBuilder(Er7foodApiApplication.class)
            .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository cadastroRestaurante = applicationContext.getBean(RestauranteRepositoryImpl.class);

        cadastroRestaurante
                .listar()
                .forEach(
                    r -> System.out.printf("%s - %f - %s\n", r.getNome(), r.getTaxaFrete(), r.getCozinha().getNome()));
        System.out.println("\n");
    }
}
