package com.er7.er7foodapi.domain.model;

import com.er7.er7foodapi.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime(2)")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime(2)")
    private OffsetDateTime dataAtualizacao;

    private Boolean ativo = Boolean.TRUE;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    private Boolean aberto = Boolean.FALSE;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany //(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar() {
       setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public boolean estaInativo() {
        return !getAtivo();
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().add(formaPagamento);
    }

    public void abrir() {
        setAberto(Boolean.TRUE);
    }

    public void fechar() {
        setAberto(Boolean.FALSE);
    }

    public boolean estaFechado() {
        return !getAberto();
    }

    public boolean possui(Usuario responsavel) {
        return getResponsaveis().contains(responsavel);
    }

    public boolean naoPossui(Usuario responsavel) {
        return !possui(responsavel);
    }

    public boolean adiciona(Usuario responsavel) {
        return getResponsaveis().add(responsavel);
    }

    public boolean remove(Usuario responsavel) {
        return getResponsaveis().remove(responsavel);
    }

    public boolean aceita(FormaPagamento formaPagamento) {
        return getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceita(FormaPagamento formaPagamento) {
        return !aceita(formaPagamento);
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isInativo() {
        return !isAtivo();
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public boolean aberturaPermitida() {
        return isAtivo() && isFechado();
    }

    public boolean ativacaoPermitida() {
        return isInativo();
    }

    public boolean inativacaoPermitida() {
        return isAtivo();
    }

    public boolean fechamentoPermitido() {
        return isAberto();
    }
}
