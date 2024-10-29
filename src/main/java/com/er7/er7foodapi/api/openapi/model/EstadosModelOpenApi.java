package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.CidadeModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenApi {

    private EstadosEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("EstadosEmbeddedModel")
    @Data
    public class EstadosEmbeddedModelOpenApi {

        private List<CidadeModel> estados;
    }
}
