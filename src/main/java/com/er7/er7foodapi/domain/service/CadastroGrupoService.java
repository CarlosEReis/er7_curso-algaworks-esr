package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.exception.GrupoNaoEncontradoException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.model.Grupo;
import com.er7.er7foodapi.domain.model.Permissao;
import com.er7.er7foodapi.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CadastroGrupoService {

    public static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso.";

    @Autowired private GrupoRepository grupoRepository;
    @Autowired private CadastroPermissaoService permissaoService;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    public Grupo buscarOuFalhar(Long grupoID) {
        return grupoRepository.findById(grupoID)
            .orElseThrow(() -> new GrupoNaoEncontradoException(grupoID));
    }

    @Transactional
    public void excluir(Long grupoID) {
        try {
            grupoRepository.deleteById(grupoID);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(grupoID);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_GRUPO_EM_USO, grupoID)
            );
        }
    }

    public Set<Permissao> permissoes(Long grupoID) {
        return buscarOuFalhar(grupoID).getPermissoes();
    }

    @Transactional
    public void desassociarPermissao(Long grupoID, Long permissaoID) {
        var grupo = buscarOuFalhar(grupoID);
        var permissao = permissaoService.buscaOuFalha(permissaoID);
        if (grupo.naoPossui(permissao))
            throw new NegocioException(String.format(
                "Grupo de código %s, não possui a permissão de código %s", grupoID, permissaoID));
        grupo.remove(permissao);
    }

    @Transactional
    public void associar(Long grupoID, Long permissaoID) {
        var grupo = buscarOuFalhar(grupoID);
        var permissao = permissaoService.buscaOuFalha(permissaoID);
        grupo.adiciona(permissao);
    }
}
