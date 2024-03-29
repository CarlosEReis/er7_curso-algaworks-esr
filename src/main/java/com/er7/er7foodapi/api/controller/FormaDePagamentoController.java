package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.FormaPagamentoInputDisassembler;
import com.er7.er7foodapi.api.assembler.FormaPagamentoModelAssembler;
import com.er7.er7foodapi.api.model.FormaPagamentoModel;
import com.er7.er7foodapi.api.model.input.FormaPagamentoInput;
import com.er7.er7foodapi.domain.model.FormaPagamento;
import com.er7.er7foodapi.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaDePagamentoController {

    @Autowired private CadastroFormaPagamentoService pagamentoService;
    @Autowired private FormaPagamentoModelAssembler pagamentoModelAssembler;
    @Autowired private FormaPagamentoInputDisassembler pagamentoDisassembler;

    // TODO: cadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = pagamentoDisassembler.toDomainObject(formaPagamentoInput);
        return  pagamentoModelAssembler.toModel(pagamentoService.salvar(formaPagamento));
    }

    // TODO: listagem
    @GetMapping
    public List<FormaPagamentoModel> listar() {
        return pagamentoModelAssembler.toCollectionModel(pagamentoService.listar());
    }

    // TODO: busca
    @GetMapping("/{formaPagtoID}")
    public FormaPagamentoModel buscar(@PathVariable Long formaPagtoID) {
        FormaPagamento formaPagamento = pagamentoService.buscarOuFalhar(formaPagtoID);
        return pagamentoModelAssembler.toModel(formaPagamento);
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
