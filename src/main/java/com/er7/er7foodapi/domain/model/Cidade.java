package com.er7.er7foodapi.domain.model;

import com.er7.er7foodapi.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Valid @NotNull @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;
}
