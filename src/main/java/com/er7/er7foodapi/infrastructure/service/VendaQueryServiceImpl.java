package com.er7.er7foodapi.infrastructure.service;

import com.er7.er7foodapi.domain.filter.VendaDiariaFilter;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.model.StatusPedido;
import com.er7.er7foodapi.domain.model.dto.VendaDiaria;
import com.er7.er7foodapi.domain.service.VendasQueryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VendaQueryServiceImpl implements VendasQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var predicates = new ArrayList<Predicate>();

        if (filtro.getClienteID() != null )
            predicates.add(builder.equal(root.get("cliente").get("id"), filtro.getClienteID()));

        if (filtro.getDataCriacaoInicio() != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));

        if (filtro.getDataCriacaoFinal() != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFinal()));

        var functionConvertTZ =
            builder
                .function(
                    "convert_tz",
                    Date.class,
                    root.get("dataCriacao"),
                    builder.literal("+00:00"),
                    builder.literal(timeOffset)); // todo: deixar de forma dinaminca com o parametro 'timeOffset'

        var functionDateDataCriacao =
            builder
                .function(
                    "date",
                    Date.class,
                    functionConvertTZ);

        predicates.add(root.get("status").in(
                StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        var selection = builder.construct(VendaDiaria.class,
            functionDateDataCriacao,
            builder.count(root.get("id")),
            builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
