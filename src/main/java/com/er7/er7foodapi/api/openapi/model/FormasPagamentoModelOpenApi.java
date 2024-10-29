package com.er7.er7foodapi.api.openapi.model;

import com.er7.er7foodapi.api.model.FormaPagamentoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("FormasPagamentoEmbeddedModel")
    @Data
    public class FormasPagamentoEmbeddedModelOpenApi {

        private List<FormaPagamentoModel> formasPagamento;

    }
}
