package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;
    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaModel> cozinhas;
    }
}
