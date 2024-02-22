package com.er7.er7foodapi.infrastructure.service.report;

import com.er7.er7foodapi.domain.filter.VendaDiariaFilter;
import com.er7.er7foodapi.domain.service.VendaReportService;
import org.springframework.stereotype.Service;

@Service
public class PdfVendaReportService implements VendaReportService {


    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffiset) {



        return new byte[0];
    }
}
