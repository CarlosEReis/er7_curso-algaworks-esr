package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.ProdutoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("ProdutosModel")
@Data
public class ProdutosModelOpenApi {

    private ProdutosEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("ProdutosEmbeddedModel")
    @Data
    public class ProdutosEmbeddedModelOpenApi {

        private List<ProdutoModel> produtos;
    }
}
