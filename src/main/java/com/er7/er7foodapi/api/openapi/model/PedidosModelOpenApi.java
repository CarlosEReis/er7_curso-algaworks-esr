package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.PedidoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("PedidosModel")
@Data
public class PedidosModelOpenApi {

    private PermissoesModelOpenApi _embedded;
    private LinksModelOpenApi _links;
    private PageModelOpenApi page;

    @ApiModel("PedidoEmbeddedModel")
    @Data
    public class PedidosEmbeddedModelOpenApi {

        private List<PedidoModel> pedidos;
    }
}
