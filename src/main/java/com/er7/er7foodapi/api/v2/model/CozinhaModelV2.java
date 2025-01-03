package com.er7.er7foodapi.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@ApiModel("CozinhaModel")
@Setter
@Getter
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {

    @ApiModelProperty(example = "1")
    //@JsonView(RestauranteView.Resumo.class)
    private Long idCozinha;

    @ApiModelProperty(example = "Brasileira")
    //@JsonView(RestauranteView.Resumo.class)
    private String nomeCozinha;
}
