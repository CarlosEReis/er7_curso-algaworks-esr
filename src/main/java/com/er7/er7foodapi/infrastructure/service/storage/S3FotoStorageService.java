package com.er7.er7foodapi.infrastructure.service.storage;

import com.er7.er7foodapi.domain.service.FotoStorageService;

import java.io.InputStream;

//@Service
public class S3FotoStorageService implements FotoStorageService {



    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void remover(String nomeArquivo) {

    }

}
