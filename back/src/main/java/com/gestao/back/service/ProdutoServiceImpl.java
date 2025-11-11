/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestao.back.service;

import com.gestao.back.dto.MovimentoEstoqueRequestDTO;
import com.gestao.back.dto.ProdutoRequestDTO;
import com.gestao.back.dto.ProdutoResponseDTO;
import com.gestao.back.model.entities.MovimentoEstoque;
import com.gestao.back.model.entities.Produto;
import com.gestao.back.model.entities.Usuario;
import com.gestao.back.model.enums.TipoMovimento;
import com.gestao.back.model.repositories.MovimentoEstoqueRepository;
import com.gestao.back.model.repositories.ProdutoRepository;
import com.gestao.back.model.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kayqu
 */
@Service
public class ProdutoServiceImpl {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MovimentoEstoqueRepository movimentoEstoqueRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto) {
        if (produtoRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código já cadastrado");
        }
        if (dto.getPrecoUnitario().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço unitário não pode ser negativo");
        }
        if (dto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Estoque inicial não pode ser negativo");
        }

        Produto produto = new Produto();
        produto.setCodigo(dto.getCodigo());
        produto.setNome(dto.getNome());
        produto.setCategoria(dto.getCategoria());
        produto.setPrecoUnitario(dto.getPrecoUnitario());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        Produto produtoSalvo = produtoRepository.save(produto);

        return new ProdutoResponseDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (dto.getPrecoUnitario().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço unitário não pode ser negativo");
        }

        produto.setCodigo(dto.getCodigo());
        produto.setNome(dto.getNome());
        produto.setCategoria(dto.getCategoria());
        produto.setPrecoUnitario(dto.getPrecoUnitario());

        Produto produtoAtualizado = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produtoAtualizado);
    }

    @Transactional
    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    @Transactional
    public ProdutoResponseDTO movimentarEstoque(Long idProduto, MovimentoEstoqueRequestDTO dto) {

        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado (ID: " + idProduto + ")"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado (ID: " + dto.getUsuarioId() + ")"));

        TipoMovimento tipo = dto.getTipo();
        int quantidadeMovimentada = dto.getQuantidade();

        if (!tipo.equals(TipoMovimento.ENTRADA) && !tipo.equals(TipoMovimento.AJUSTE)) {
            throw new IllegalArgumentException("Tipo de movimento inválido. Use 'ENTRADA' ou 'AJUSTE'.");
        }

        if (tipo.equals(TipoMovimento.ENTRADA) && quantidadeMovimentada <= 0) {
            throw new IllegalArgumentException("Para ENTRADA, a quantidade deve ser positiva.");
        }
        if (quantidadeMovimentada == 0) {
            throw new IllegalArgumentException("A quantidade da movimentação não pode ser zero.");
        }

        int estoqueAtual = produto.getQuantidadeEstoque();
        int novoEstoque = estoqueAtual + quantidadeMovimentada;

        if (novoEstoque < 0) {
            throw new RuntimeException("Estoque não pode ficar negativo. (Estoque Atual: " + estoqueAtual + ", Tentativa de Ajuste: " + quantidadeMovimentada + ")");
        }

        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setProduto(produto);
        movimento.setUsuario(usuario);
        movimento.setTipo(tipo);
        movimento.setQuantidade(quantidadeMovimentada);
        movimento.setData(LocalDateTime.now());
        movimento.setMotivo(dto.getMotivo());
        movimentoEstoqueRepository.save(movimento);

        produto.setQuantidadeEstoque(novoEstoque);
        Produto produtoAtualizado = produtoRepository.save(produto);

        return new ProdutoResponseDTO(produtoAtualizado);
    }
}
