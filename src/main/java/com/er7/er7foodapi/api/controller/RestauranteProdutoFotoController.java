package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.FotoProdutoModelAssembler;
import com.er7.er7foodapi.api.model.FotoProdutoModel;
import com.er7.er7foodapi.api.model.input.FotoProdutoInput;
import com.er7.er7foodapi.domain.model.FotoProduto;
import com.er7.er7foodapi.domain.model.Produto;
import com.er7.er7foodapi.domain.service.CadastroProdutoService;
import com.er7.er7foodapi.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/produtos/{produtoID}/foto")
public class RestauranteProdutoFotoController {

    @Autowired private CadastroProdutoService cadastroProduto;
    @Autowired private CatalogoFotoProdutoService catalogoFotoProduto;
    @Autowired private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteID, @PathVariable Long produtoID, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = cadastroProduto.buscarOuFalhar(restauranteID, produtoID);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());
        return fotoProdutoModelAssembler.toModel(fotoSalva);
    }
}
