package com.er7.er7foodapi.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.er7.er7foodapi.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

//@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

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
