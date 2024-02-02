package com.er7.er7foodapi.core.jackson;

import com.er7.er7foodapi.api.model.mixin.RestauranteMixin;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }
}
