package com.er7.er7foodapi.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("er7food.email")
public class EmailProperties {

    @NotNull
    private String remetente;
    private Implementacao impl = Implementacao.FAKE;
    private SandBox sandBox = new SandBox();

    @Getter
    @Setter
    public class SandBox {
        private String destinatario;
    }

    public enum Implementacao {
        FAKE, SMTP, SANDBOX
    }
}
