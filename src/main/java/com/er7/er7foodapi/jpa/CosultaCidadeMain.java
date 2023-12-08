package com.er7.er7foodapi.jpa;

import com.er7.er7foodapi.Er7foodApiApplication;
import com.er7.er7foodapi.domain.repository.CidadeRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class CosultaCidadeMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(Er7foodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run();

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);

        cidadeRepository.listar()
            .forEach(
                c -> System.out.printf("%d - %s -- %s\n",
                    c.getId(),
                    c.getNome(),
                    c.getEstado().getNome()));
    }
}
