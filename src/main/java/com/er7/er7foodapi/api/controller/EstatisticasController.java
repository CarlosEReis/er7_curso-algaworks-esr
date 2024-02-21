package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.filter.VendaDiariaFilter;
import com.er7.er7foodapi.domain.model.dto.VendaDiaria;
import com.er7.er7foodapi.domain.service.VendasQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendasQueryService vendasQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        return vendasQueryService.consultaVendasDiarias(filtro);
    }
}
