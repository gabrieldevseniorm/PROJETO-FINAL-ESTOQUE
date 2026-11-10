package br.senai.sistema.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.model.Fornecedor;
import br.senai.sistema.model.Movimentacao;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Produto;
import br.senai.sistema.model.TipoMovimentacao;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.CategoriaRepository;
import br.senai.sistema.repository.FornecedorRepository;
import br.senai.sistema.repository.MovimentacaoRepository;
import br.senai.sistema.repository.ProdutoRepository;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Carga inicial de dados: executa uma vez sempre que a aplicação sobe.
 * Cada bloco só insere os dados se a tabela ainda estiver vazia (por isso não duplica).
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final FornecedorRepository fornecedorRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository,
                         MovimentacaoRepository movimentacaoRepository,
                         FornecedorRepository fornecedorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Almoxarifado", "almoxarife",
                    passwordEncoder.encode("almoxarife123"), Perfil.ALMOXARIFE));
            usuarioRepository.save(new Usuario("Funcionário", "operador",
                    passwordEncoder.encode("operador123"), Perfil.OPERADOR));
        }
                
        if (usuarioRepository.findByLogin("Gabriel").isEmpty()) {
            usuarioRepository.save(new Usuario("Gabriel Henrique", "Gabriel",
                    passwordEncoder.encode("Gabriel321"), Perfil.CLIENTE));
        }

        if (categoriaRepository.count() == 0) {
            
            Categoria categoria1 = new Categoria(
             "Cadeiras",
        "Cadeiras para ambientes internos e externos"
            );

            Categoria categoria2 = new Categoria(
             "Poltronas",
        "Poltronas para áreas externas e espaços de lazer"
            );

            Categoria categoria3 = new Categoria(
             "Sofás",
        "Sofás e módulos para ambientes externos"
            );

            Categoria categoria4 = new Categoria(
             "Mesas",
        "Mesas de centro, laterais e mesas de apoio"
            );

            Categoria categoria5 = new Categoria(
             "Espreguiçadeiras",
        "Móveis para piscinas e áreas de descanso"
            );

            Categoria categoria6 = new Categoria(
             "Banquetas e bancos",
        "Bancos e banquetas para áreas externas"
            );

            Categoria categoria7 = new Categoria(
             "Pufes e acessórios",
        "Complementos e acessórios para mobiliário"
            );

            categoriaRepository.save(categoria1);
            categoriaRepository.save(categoria2);
            categoriaRepository.save(categoria3);
            categoriaRepository.save(categoria4);
            categoriaRepository.save(categoria5);
            categoriaRepository.save(categoria6);
            categoriaRepository.save(categoria7);
        }

        if (produtoRepository.count() == 0) {
            
            Produto produto1 = new Produto(
        "MA-001", "Cadeira Wave", "UN",
                new BigDecimal("850.00"), 10, 25
            );

            Produto produto2 = new Produto(
        "MA-002", "Espreguiçadeira Wave", "UN",
                new BigDecimal("1450.00"), 5, 12
            );

            Produto produto3 = new Produto(
        "MA-003", "Cadeira Aura", "UN",
                new BigDecimal("950.00"), 10, 20
            );

            Produto produto4 = new Produto(
        "MA-004", "Mesa Aura", "UN",
                new BigDecimal("1200.00"), 5, 10
            );

            Produto produto5 = new Produto(
        "MA-005", "Mesa Lateral Bossa", "UN",
                new BigDecimal("650.00"), 5, 15
            );

            produto1.setLocalizacao("Estoque de móveis");
            produto2.setLocalizacao("Área de produtos finalizados");
            produto3.setLocalizacao("Estoque de móveis");
            produto4.setLocalizacao("Área de produtos finalizados");
            produto5.setLocalizacao("Estoque de móveis");

            List<Categoria> categorias =
                categoriaRepository.findAll(Sort.by("id"));

            if (categorias.size() >= 5) {
                produto1.setCategoria(categorias.get(0));
                produto2.setCategoria(categorias.get(4));
                produto3.setCategoria(categorias.get(0));
                produto4.setCategoria(categorias.get(3));
                produto5.setCategoria(categorias.get(3));
            }

            produtoRepository.save(produto1);
            produtoRepository.save(produto2);
            produtoRepository.save(produto3);
            produtoRepository.save(produto4);
            produtoRepository.save(produto5);
        }

        // Operação principal: cada registro aponta para registros que já existem (relacionamento).
        // O "%" (resto da divisão) evita erro se existirem menos de 3 registros cadastrados.
        if (movimentacaoRepository.count() == 0) {
            List<Produto> produtos = produtoRepository.findAll(Sort.by("id"));
            if (!produtos.isEmpty()) {
                Movimentacao movimentacao1 = new Movimentacao(TipoMovimentacao.ENTRADA, 200, LocalDate.of(2026, 10, 14),
                        produtos.get(0));
                movimentacao1.setObservacao("Entrada de móveis finalizados no estoque");
                Movimentacao movimentacao2 = new Movimentacao(TipoMovimentacao.SAIDA, 2, LocalDate.of(2026, 10, 15),
                        produtos.get(1 % produtos.size()));
                movimentacao2.setObservacao("Saída de móveis para comercialização");
                Movimentacao movimentacao3 = new Movimentacao(TipoMovimentacao.ENTRADA, 10, LocalDate.of(2026, 10, 16),
                        produtos.get(2 % produtos.size()));
                movimentacao3.setObservacao("Entrada de produtos após processo de fabricação");
                movimentacaoRepository.save(movimentacao1);
                movimentacaoRepository.save(movimentacao2);
                movimentacaoRepository.save(movimentacao3);
            }
        }

        // Nova entidade (guia de personalização)
        if (fornecedorRepository.count() == 0) {
            Fornecedor fornecedor1 = new Fornecedor("Distribuidora Paraná", "12.345.678/0001-90", "(43) 3321-1000");
            Fornecedor fornecedor2 = new Fornecedor("Casa do Parafuso", "23.456.789/0001-01", "(43) 3322-2000");
            Fornecedor fornecedor3 = new Fornecedor("EPI Sul", "34.567.890/0001-12", "(43) 3323-3000");
            fornecedorRepository.save(fornecedor1);
            fornecedorRepository.save(fornecedor2);
            fornecedorRepository.save(fornecedor3);
        }
    }
}