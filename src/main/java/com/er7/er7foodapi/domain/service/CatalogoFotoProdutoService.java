package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.FotoProdutoNaoEncontradaException;
import com.er7.er7foodapi.domain.model.FotoProduto;
import com.er7.er7foodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteID = foto.getRestauranteID();
        Long produtoID = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteID, produtoID);
        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        };

        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto
            .builder()
            .nomeArquivo(foto.getNomeArquivo())
            .inputStream(dadosArquivo)
            .build();

        if (nomeArquivoExistente != null ) fotoStorage.remover(nomeArquivoExistente);
        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscarOuFalhar(Long restauranteID, Long produtoID) {
       return produtoRepository.findFotoById(restauranteID, produtoID)
               .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteID, produtoID));
    }
}
