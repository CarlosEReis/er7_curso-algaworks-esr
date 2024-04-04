package com.er7.er7foodapi.core.email;

import com.er7.er7foodapi.domain.service.EnvioEmailService;
import com.er7.er7foodapi.infrastructure.email.FakeEnvioEmailService;
import com.er7.er7foodapi.infrastructure.email.SandBoxEnvioEmailService;
import com.er7.er7foodapi.infrastructure.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        return switch (emailProperties.getImpl()) {
            case FAKE -> new FakeEnvioEmailService();
            case SMTP -> new SmtpEnvioEmailService();
            case SANDBOX -> new SandBoxEnvioEmailService();
            default -> null;
        };
    }
}
