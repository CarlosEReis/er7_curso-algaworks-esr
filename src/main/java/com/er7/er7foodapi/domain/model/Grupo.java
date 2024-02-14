package com.er7.er7foodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
        joinColumns = @JoinColumn(name = "grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public Boolean possui(Permissao permissao) {
        return getPermissoes().contains(permissao);
    }

    public Boolean naoPossui(Permissao permissao) {
        return !possui(permissao);
    }

    public Boolean remove(Permissao permissao) {
        return getPermissoes().remove(permissao);
    }

    public Boolean adiciona(Permissao permissao) {
        return getPermissoes().add(permissao);
    }
}
