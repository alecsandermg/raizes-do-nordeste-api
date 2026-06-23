package com.raizesdonordeste.application.service;

import org.springframework.stereotype.Service;

import com.raizesdonordeste.api.dto.pagamento.PagamentoRequest;
import com.raizesdonordeste.api.dto.pagamento.PagamentoResponse;
import com.raizesdonordeste.domain.entity.Pagamento;
import com.raizesdonordeste.domain.entity.Pedido;
import com.raizesdonordeste.domain.enums.StatusPagamento;
import com.raizesdonordeste.infrastructure.repository.PagamentoRepository;
import com.raizesdonordeste.infrastructure.repository.PedidoRepository;
import com.raizesdonordeste.application.service.AuditService;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final FidelidadeService fidelidadeService;
    private final AuditService auditService;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            PedidoRepository pedidoRepository,
            FidelidadeService fidelidadeService,
            AuditService auditService) {

        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
        this.fidelidadeService = fidelidadeService;
        this.auditService = auditService;
    }

    public PagamentoResponse processar(
            Long pedidoId,
            PagamentoRequest request) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));

        Pagamento pagamento = Pagamento.builder()
                .pedido(pedido)
                .build();

        if (Boolean.TRUE.equals(request.getAprovado())) {

            pagamento.setStatus(StatusPagamento.APROVADO);

            Integer pontos =
                    pedido.getValorTotal().intValue();

            fidelidadeService.adicionarPontos(
                    pedido.getUsuario().getId(),
                    pontos);
            auditService.registrar(
                    "PAGAMENTO",
                    "APROVADO",
                    pedido.getUsuario().getEmail());

        } else {

            pagamento.setStatus(StatusPagamento.RECUSADO);
            auditService.registrar(
                    "PAGAMENTO",
                    "RECUSADO",
                    pedido.getUsuario().getEmail());
        }

        pagamento = pagamentoRepository.save(pagamento);

        PagamentoResponse response =
                new PagamentoResponse();

        response.setId(pagamento.getId());
        response.setPedidoId(pedido.getId());
        response.setStatus(
                pagamento.getStatus().name());
        
        

        return response;
    }
}