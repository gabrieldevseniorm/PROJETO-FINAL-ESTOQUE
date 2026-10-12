package br.senai.sistema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade Categoria: representa uma categoria de produtos.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "categorias" no banco.
 * Cada atributo vira UMA COLUNA dessa tabela.
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome da categoria. */
    @Column(nullable = false, length = 100)
    private String nome;

    /** Descrição da categoria. */
    @Column(length = 255)
    private String descricao;

    /** Construtor vazio: obrigatório para o JPA. */
    public Categoria() {
    }

    /** Construtor com os dados principais. */
    public Categoria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
