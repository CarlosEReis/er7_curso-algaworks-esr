package com.er7.er7foodapi.infrastructure.service;

import com.er7.er7foodapi.domain.filter.VendaDiariaFilter;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.model.dto.VendaDiaria;
import com.er7.er7foodapi.domain.service.VendasQueryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Service
public class VendaQueryServiceImpl implements VendasQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filtro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionDateDataCriacao =
            builder
                .function("date", Date.class, root.get("dataCriacao"));

        var selection = builder.construct(VendaDiaria.class,
            functionDateDataCriacao,
            builder.count(root.get("id")),
            builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);
        return manager.createQuery(query).getResultList();
    }
}
