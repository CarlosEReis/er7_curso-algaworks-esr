package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.PedidoResumoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("PedidosResumoModel")
@Getter
@Setter
public class PedidosResumoModelOpenApi {

    private PedidosResumoEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;
    private PageModelOpenApi page;

    @ApiModel("PedidosResumoEmbeddedModel")
    @Data
    public class PedidosResumoEmbeddedModelOpenApi {

        private List<PedidoResumoModel> pedidos;

    }
}
