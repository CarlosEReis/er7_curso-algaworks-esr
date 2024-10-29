package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.UsuarioModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("UsuariosModel")
@Data
public class UsuariosModelOpenApi {

    private UsuariosEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("UsuariosEmbeddedModel")
    @Data
    public class UsuariosEmbeddedModelOpenApi {

        private List<UsuarioModel> usuarios;

    }
}