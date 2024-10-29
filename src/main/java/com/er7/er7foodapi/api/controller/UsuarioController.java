package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.UsuarioInputDisassembler;
import com.er7.er7foodapi.api.assembler.UsuarioModelAssembler;
import com.er7.er7foodapi.api.model.UsuarioModel;
import com.er7.er7foodapi.api.model.input.SenhaInput;
import com.er7.er7foodapi.api.model.input.UsuarioComSenhaInput;
import com.er7.er7foodapi.api.model.input.UsuarioInput;
import com.er7.er7foodapi.api.openapi.controller.UsuarioControllerOpenApi;
import com.er7.er7foodapi.domain.model.Usuario;
import com.er7.er7foodapi.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired private CadastroUsuarioService usuarioService;
    @Autowired private UsuarioModelAssembler usuarioModelAssembler;
    @Autowired private UsuarioInputDisassembler usuarioInputDisassembler;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        return usuarioModelAssembler.toModel(usuarioService.salvar(usuario));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UsuarioModel> listar() {
        var todosUsuarios = usuarioService.listar();
        return usuarioModelAssembler.toCollectionModel(todosUsuarios);
    }

    @GetMapping(path = "/{usuarioID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel buscar(@PathVariable Long usuarioID) {
        return usuarioModelAssembler.toModel(usuarioService.buscarOuFalhar(usuarioID));
    }

    @PutMapping(path = "/{usuarioID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel atualizar(@PathVariable Long usuarioID, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioDB = usuarioService.buscarOuFalhar(usuarioID);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioDB);
        return usuarioModelAssembler.toModel(usuarioService.salvar(usuarioDB));
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }

    @DeleteMapping("/{usuarioID}")
    public void excluir(@PathVariable Long usuarioID) {
        usuarioService.deleta(usuarioID);
    }

}
