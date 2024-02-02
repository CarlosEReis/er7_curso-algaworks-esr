package com.er7.er7foodapi.domain.model;

import com.er7.er7foodapi.core.validation.Groups;
import com.er7.er7foodapi.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ValorZeroIncluiDescricao(
        valorField = "taxaFrete",
        descricaoField = "nome",
        descricaoObrigatoria = "Frete Grátis")
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime(2)")
    private OffsetDateTime dataCadastro;

    //@JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime(2)")
    private OffsetDateTime dataAtualizacao;

    @NotBlank(message = "Nome do restaurante não pode ser nulo")
    @Column(nullable = false)
    private String nome;

    @NotNull //@TaxaFrete @Multiplo(numero = 5)
    //@PositiveOrZero(message = "{TaxaFrete.invalida}")
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    //@JsonIgnoreProperties("hibernateLazyInitializer")
    //@JsonIgnore
    //@JsonIgnoreProperties(value = "nome", allowGetters = true)
    @Valid @NotNull @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    //@JsonIgnore
    @Embedded
    private Endereco endereco;

    //@JsonIgnore
    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurante_forma_pagamento",
        joinColumns = @JoinColumn(name = "restaurante_id"),
        inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    //@JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();
}
