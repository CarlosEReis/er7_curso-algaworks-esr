package com.er7.er7foodapi.api.v1.openapi.model;

import com.er7.er7foodapi.api.v1.model.RestauranteModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("RestaurantesModel")
@Data
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("RestaurantesEmbeddedModel")
    @Data
    public class RestaurantesEmbeddedModelOpenApi {

        private List<RestauranteModel> restaurantes;
    }
}
