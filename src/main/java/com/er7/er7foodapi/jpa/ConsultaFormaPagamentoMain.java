package com.er7.er7foodapi.jpa;

import com.er7.er7foodapi.Er7foodApiApplication;
import com.er7.er7foodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaFormaPagamentoMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(Er7foodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run();

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

        formaPagamentoRepository.listar()
            .forEach(
                f -> System.out.printf("%d - %s\n",
                    f.getId(),
                    f.getDescricao())
            );
    }
}
