package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.CidadeModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadesEmbeddedModelOpenApi {

        private List<CidadeModel> cidades;
    }
}
