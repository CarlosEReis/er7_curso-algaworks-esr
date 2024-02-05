package com.er7.er7foodapi.core.jackson;

import com.er7.er7foodapi.api.model.mixin.CidadeMixin;
import com.er7.er7foodapi.api.model.mixin.CozinhaMixin;
import com.er7.er7foodapi.domain.model.Cidade;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
