package br.senai.sistema.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade Movimentacao: representa uma entrada ou saída
 * de produtos no estoque.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela
 * "movimentacoes" no banco de dados.
 */
@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Tipo da movimentação: ENTRADA, SAIDA ou VENDA. */
    @Column(nullable = false, length = 20)
    private String tipo;

    /** Quantidade de produtos movimentados. */
    @Column(nullable = false)
    private Integer quantidade;

    /** Valor por unidade na movimentação. */
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valorUnitario;

    /** Observações sobre a movimentação. */
    @Column(length = 255)
    private String observacao;

    /** Construtor vazio obrigatório para o JPA. */
    public Movimentacao() {
    }

    /** Construtor com os dados principais. */
    public Movimentacao(String tipo, Integer quantidade,
                        BigDecimal valorUnitario, String observacao) {
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.observacao = observacao;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
