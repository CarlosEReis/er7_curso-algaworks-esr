package com.er7.er7foodapi.jpa;

import com.er7.er7foodapi.Er7foodApiApplication;
import com.er7.er7foodapi.domain.repository.PermissaoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaPermissaoMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(Er7foodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run();

        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

        permissaoRepository.listar()
            .forEach(p -> System.out.printf("%d - %s -- %s\n", p.getId(), p.getNome(), p.getDescricao()));
    }
}
