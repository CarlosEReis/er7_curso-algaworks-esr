package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.PermissaoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("PermissoesModel")
@Data
public class PermissoesModelOpenApi {

    private PermissoesEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("PermissoesEmbeddedModel")
    @Data
    public class PermissoesEmbeddedModelOpenApi {

        private List<PermissaoModel> permissoes;

    }
}