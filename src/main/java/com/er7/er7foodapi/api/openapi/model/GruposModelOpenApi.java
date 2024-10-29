package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.GrupoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

    private GruposEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("GrupoEmbeddedModel")
    @Data
    public class GruposEmbeddedModelOpenApi {

        private List<GrupoModel> grupos;
    }
}
