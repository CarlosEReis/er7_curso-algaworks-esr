package com.er7.er7foodapi.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@ApiModel("CidadeModel")
@Getter
@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

    @ApiModelProperty(value = "ID da cidade", example = "1")
    private Long idCidade;

    @ApiModelProperty(value = "Nome da cidade", example = "Uberlândia")
    private String nomeCidade;

    private Long idEstado;

    private String nomeEstado;

}
