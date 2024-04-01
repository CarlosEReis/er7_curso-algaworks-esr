package com.er7.er7foodapi.infrastructure.email;

import com.er7.er7foodapi.core.email.EmailProperties;
import com.er7.er7foodapi.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired private JavaMailSender mailSender;
    @Autowired private EmailProperties emailProperties;

    @Override
    public void enviar(Menssagem menssagem) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(menssagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(menssagem.getAssunto());
            helper.setText(menssagem.getCorpo(), true);

        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o e-mail.");
        }
    }
}
