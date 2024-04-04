package com.er7.er7foodapi.infrastructure.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Mensagem menssagem) {
        String corpo = processarTemplate(menssagem);
        log.info("[FAKE E-MAIL] Para: {}\n{}", menssagem.getDestinatarios(), corpo);
    }
}
