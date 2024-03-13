package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.FotoProdutoModelAssembler;
import com.er7.er7foodapi.api.model.FotoProdutoModel;
import com.er7.er7foodapi.api.model.input.FotoProdutoInput;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.model.FotoProduto;
import com.er7.er7foodapi.domain.model.Produto;
import com.er7.er7foodapi.domain.service.CadastroProdutoService;
import com.er7.er7foodapi.domain.service.CatalogoFotoProdutoService;
import com.er7.er7foodapi.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/produtos/{produtoID}/foto")
public class RestauranteProdutoFotoController {

    @Autowired private CadastroProdutoService cadastroProduto;
    @Autowired private CatalogoFotoProdutoService catalogoFotoProduto;
    @Autowired private FotoProdutoModelAssembler fotoProdutoModelAssembler;
    @Autowired private FotoStorageService fotoStorageService;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscar(@PathVariable Long restauranteID, @PathVariable Long produtoID) {
        var fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteID, produtoID);
        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteID, @PathVariable Long produtoID) {
        try {
            FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteID, produtoID);
            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
