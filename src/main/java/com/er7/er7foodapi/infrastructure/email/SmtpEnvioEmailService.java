package com.er7.er7foodapi.infrastructure.email;

import com.er7.er7foodapi.core.email.EmailProperties;
import com.er7.er7foodapi.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired private JavaMailSender mailSender;
    @Autowired private EmailProperties emailProperties;
    @Autowired private Configuration freemarkerConfig;

    @Override
    public void enviar(Menssagem menssagem) {
        try {
            String corpo = processarTemplate(menssagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(menssagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(menssagem.getAssunto());
            helper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o e-mail.", e.getCause());
        }
    }

    private String processarTemplate(Menssagem menssagem) {
        try {
            Template template = freemarkerConfig.getTemplate(menssagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, menssagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail", e);
        }
    }
}
