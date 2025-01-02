package com.er7.er7foodapi.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EstadoModel extends RepresentationModel<EstadoModel> {

    @ApiModelProperty(value = "ID do estado", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome do estado", example = "Minas Gerais")
    private String nome;
}
