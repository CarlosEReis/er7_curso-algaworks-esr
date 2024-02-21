package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.filter.VendaDiariaFilter;
import com.er7.er7foodapi.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendasQueryService {

    List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
