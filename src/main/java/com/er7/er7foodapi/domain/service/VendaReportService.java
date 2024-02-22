package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffiset);
}
