package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.exception.UsuarioNaoEncontradoException;
import com.er7.er7foodapi.domain.model.Usuario;
import com.er7.er7foodapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroUsuarioService {

    public static final String MSG_USUARIO_EM_USO = "Usuario de código %d não pode ser removido, pois está em uso.";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioDB = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioDB.isPresent() && !usuarioDB.get().equals(usuario)) {
            throw new NegocioException(
                String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarOuFalhar(Long usuarioID) {
        return usuarioRepository.findById(usuarioID)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioID));
    }

    @Transactional
    public void deleta(Long usuarioID) {
        try {
            usuarioRepository.deleteById(usuarioID);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(usuarioID);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_USUARIO_EM_USO, usuarioID));
        }
    }
}
