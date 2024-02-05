package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.input.RestauranteInput;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassenbler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        // org.hibernate.HibernateException: identifier of an instance of
        // com.er7.er7foodapi.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInput, restaurante);
    }
}
