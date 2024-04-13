package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.FormaPagamentoInputDisassembler;
import com.er7.er7foodapi.api.assembler.FormaPagamentoModelAssembler;
import com.er7.er7foodapi.api.model.FormaPagamentoModel;
import com.er7.er7foodapi.api.model.input.FormaPagamentoInput;
import com.er7.er7foodapi.domain.model.FormaPagamento;
import com.er7.er7foodapi.domain.repository.FormaPagamentoRepository;
import com.er7.er7foodapi.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaDePagamentoController {

    @Autowired private CadastroFormaPagamentoService pagamentoService;
    @Autowired private FormaPagamentoModelAssembler pagamentoModelAssembler;
    @Autowired private FormaPagamentoInputDisassembler pagamentoDisassembler;

    @Autowired private FormaPagamentoRepository formaPagamentoRepository;

    // TODO: cadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = pagamentoDisassembler.toDomainObject(formaPagamentoInput);
        return  pagamentoModelAssembler.toModel(pagamentoService.salvar(formaPagamento));
    }

    // TODO: listagem
    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest webRequest) {
        ShallowEtagHeaderFilter.disableContentCaching(webRequest.getRequest());

        String etag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
        if (dataUltimaAtualizacao != null)
            etag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        if (webRequest.checkNotModified(etag))
            return null;

        var formasPagamentotoModel = pagamentoModelAssembler.toCollectionModel(pagamentoService.listar());
        return ResponseEntity
            .ok()
            //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
            .eTag(etag)
            //.cacheControl(CacheControl.noCache())
            //.cacheControl(CacheControl.noStore())
            .body(formasPagamentotoModel);
    }

    // TODO: busca
    @GetMapping("/{formaPagtoID}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagtoID) {
        FormaPagamento formaPagamento = pagamentoService.buscarOuFalhar(formaPagtoID);
        var formaPagamentoModel = pagamentoModelAssembler.toModel(formaPagamento);
        return ResponseEntity
            .ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
            .body(formaPagamentoModel);
    }

    // TODO: atualização
    @PutMapping("/{formaPagtoID}")
    public FormaPagamentoModel atualizar(@PathVariable Long formaPagtoID, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoDB = pagamentoService.buscarOuFalhar(formaPagtoID);
        pagamentoDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoDB);
        return pagamentoModelAssembler.toModel(pagamentoService.salvar(formaPagamentoDB));
    }

    // TODO: exclusao
    @DeleteMapping("{formaPagtoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagtoID) {
        pagamentoService.excluir(formaPagtoID);
    }
}
