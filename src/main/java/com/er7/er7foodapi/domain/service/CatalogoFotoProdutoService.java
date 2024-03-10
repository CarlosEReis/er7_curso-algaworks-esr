package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.model.FotoProduto;
import com.er7.er7foodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        Long restauranteID = foto.getRestauranteID();
        Long produtoID = foto.getProduto().getId();

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteID, produtoID);
        fotoExistente.ifPresent(fotoProduto -> produtoRepository.delete(fotoProduto));

        return produtoRepository.save(foto);
    }
}
