package com.raizesdonordeste.application.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raizesdonordeste.api.dto.pedido.ItemPedidoRequest;
import com.raizesdonordeste.api.dto.pedido.ItemPedidoResponse;
import com.raizesdonordeste.api.dto.pedido.PedidoRequest;
import com.raizesdonordeste.api.dto.pedido.PedidoResponse;
import com.raizesdonordeste.domain.entity.Estoque;
import com.raizesdonordeste.domain.entity.ItemPedido;
import com.raizesdonordeste.domain.entity.Pedido;
import com.raizesdonordeste.domain.entity.Produto;
import com.raizesdonordeste.domain.entity.Usuario;
import com.raizesdonordeste.domain.entity.Unidade;
import com.raizesdonordeste.infrastructure.repository.EstoqueRepository;
import com.raizesdonordeste.infrastructure.repository.ItemPedidoRepository;
import com.raizesdonordeste.infrastructure.repository.PedidoRepository;
import com.raizesdonordeste.infrastructure.repository.ProdutoRepository;
import com.raizesdonordeste.infrastructure.repository.UnidadeRepository;
import com.raizesdonordeste.infrastructure.repository.UsuarioRepository;
import com.raizesdonordeste.domain.enums.CanalPedido;
import com.raizesdonordeste.api.dto.pedido.PedidoStatusRequest;
import com.raizesdonordeste.domain.enums.StatusPedido;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;
    private final AuditService auditService;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ItemPedidoRepository itemPedidoRepository,
            UsuarioRepository usuarioRepository,
            UnidadeRepository unidadeRepository,
            ProdutoRepository produtoRepository,
            EstoqueRepository estoqueRepository,
            AuditService auditService) {

        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
        this.auditService = auditService;
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest request) {

        Usuario usuario = usuarioRepository.findById(
                request.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Unidade unidade = unidadeRepository.findById(
                request.getUnidadeId())
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada"));

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .unidade(unidade)
                .canalPedido(
                    CanalPedido.valueOf(
                        request.getCanalPedido().toUpperCase()))
                .valorTotal(BigDecimal.ZERO)
                .build();

        pedido = pedidoRepository.save(pedido);

        BigDecimal valorTotal = BigDecimal.ZERO;

        List<ItemPedidoResponse> itensResponse =
                new ArrayList<>();

        for (ItemPedidoRequest item :
                request.getItens()) {

            Produto produto =
                    produtoRepository.findById(
                            item.getProdutoId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Produto não encontrado"));

            Estoque estoque =
                    estoqueRepository
                    .findByUnidadeIdAndProdutoId(
                            unidade.getId(),
                            produto.getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Produto sem estoque"));

            if (estoque.getQuantidade()
                    < item.getQuantidade()) {

                throw new RuntimeException(
                        "Estoque insuficiente");
            }

            estoque.setQuantidade(
                    estoque.getQuantidade()
                    - item.getQuantidade());

            estoqueRepository.save(estoque);

            ItemPedido itemPedido =
                    ItemPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(item.getQuantidade())
                    .precoUnitario(
                            produto.getPrecoBase())
                    .build();

            itemPedidoRepository.save(itemPedido);

            valorTotal = valorTotal.add(
                    produto.getPrecoBase()
                    .multiply(
                            BigDecimal.valueOf(
                                    item.getQuantidade())));

            ItemPedidoResponse itemResponse =
                    new ItemPedidoResponse();

            itemResponse.setProdutoId(
                    produto.getId());

            itemResponse.setProdutoNome(
                    produto.getNome());

            itemResponse.setQuantidade(
                    item.getQuantidade());

            itemResponse.setPrecoUnitario(
                    produto.getPrecoBase());

            itensResponse.add(itemResponse);
        }

        pedido.setValorTotal(valorTotal);

        pedidoRepository.save(pedido);

        PedidoResponse response =
                new PedidoResponse();

        response.setId(pedido.getId());
        response.setStatus(
                pedido.getStatus().name());
        response.setValorTotal(valorTotal);
        response.setItens(itensResponse);
        
        response.setCanalPedido(
                pedido.getCanalPedido().name());
        
        

        return response;
    }
    @Transactional
    public PedidoResponse alterarStatus(
            Long pedidoId,
            PedidoStatusRequest request) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"));

        StatusPedido novoStatus =
                StatusPedido.valueOf(
                        request.getStatus().toUpperCase());

        StatusPedido statusAtual =
                pedido.getStatus();

        if (statusAtual == StatusPedido.ENTREGUE ||
                statusAtual == StatusPedido.CANCELADO) {

            throw new RuntimeException(
                    "Pedido finalizado não pode ser alterado");
        }

        pedido.setStatus(novoStatus);

        pedidoRepository.save(pedido);

        auditService.registrar(
                "PEDIDO",
                "STATUS_" + novoStatus.name(),
                pedido.getUsuario().getEmail());

        PedidoResponse response =
                new PedidoResponse();

        response.setId(pedido.getId());
        response.setStatus(
                pedido.getStatus().name());
        response.setValorTotal(
                pedido.getValorTotal());
        response.setCanalPedido(
                pedido.getCanalPedido().name());

        return response;
    }
    @Transactional
    public PedidoResponse cancelar(
            Long pedidoId) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"));

        if (pedido.getStatus() != StatusPedido.RECEBIDO
                && pedido.getStatus() != StatusPedido.EM_PREPARACAO) {

            throw new RuntimeException(
                    "Pedido não pode mais ser cancelado");
        }
        
        List<ItemPedido> itens =
                itemPedidoRepository.findByPedidoId(
                        pedido.getId());

        for (ItemPedido item : itens) {

            Estoque estoque =
                    estoqueRepository
                    .findByUnidadeIdAndProdutoId(
                            pedido.getUnidade().getId(),
                            item.getProduto().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Estoque não encontrado"));

            estoque.setQuantidade(
                    estoque.getQuantidade()
                    + item.getQuantidade());

            estoqueRepository.save(estoque);
        }
        
        pedido.setStatus(StatusPedido.CANCELADO);

        pedidoRepository.save(pedido);

        auditService.registrar(
                "PEDIDO",
                "CANCELADO",
                pedido.getUsuario().getEmail());

        PedidoResponse response =
                new PedidoResponse();

        response.setId(pedido.getId());
        response.setStatus(
                pedido.getStatus().name());
        response.setValorTotal(
                pedido.getValorTotal());
        response.setCanalPedido(
                pedido.getCanalPedido().name());

        return response;
    }
}