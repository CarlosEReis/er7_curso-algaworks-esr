package com.er7.er7foodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
//@ApiModel(value = "CidadeModel", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @ApiModelProperty(value = "ID da cidade", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome da cidade", example = "Uberlândia")
    private String nome;
    private EstadoModel estado;
}
