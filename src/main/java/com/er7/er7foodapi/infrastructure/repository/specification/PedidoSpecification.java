package com.er7.er7foodapi.infrastructure.repository.specification;

import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecification {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, builder) -> {

            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

            var predicates = new ArrayList<Predicate>();

            if (filtro.getClienteID() != null)
                predicates.add(builder.equal(root.get("cliente"), filtro.getClienteID()));

            if (filtro.getRestauranteID() != null)
                predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteID()));

            if (filtro.getDataCriacaoInicio() != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));

            if (filtro.getDataCriacaoFim() != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
