package com.raizesdonordeste.application.service;

import org.springframework.stereotype.Service;

import com.raizesdonordeste.api.dto.fidelidade.FidelidadeResponse;
import com.raizesdonordeste.domain.entity.FidelidadeCliente;
import com.raizesdonordeste.domain.entity.MovimentacaoPontos;
import com.raizesdonordeste.domain.entity.Usuario;
import com.raizesdonordeste.domain.enums.TipoMovimentacaoPontos;
import com.raizesdonordeste.infrastructure.repository.FidelidadeClienteRepository;
import com.raizesdonordeste.infrastructure.repository.MovimentacaoPontosRepository;
import com.raizesdonordeste.infrastructure.repository.UsuarioRepository;

@Service
public class FidelidadeService {

    private final FidelidadeClienteRepository fidelidadeRepository;
    private final MovimentacaoPontosRepository movimentacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public FidelidadeService(
            FidelidadeClienteRepository fidelidadeRepository,
            MovimentacaoPontosRepository movimentacaoRepository,
            UsuarioRepository usuarioRepository) {

        this.fidelidadeRepository = fidelidadeRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void adicionarPontos(
            Long usuarioId,
            Integer pontos) {

        Usuario usuario = usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"));

        FidelidadeCliente fidelidade =
                fidelidadeRepository
                .findByUsuarioId(usuarioId)
                .orElse(null);

        if (fidelidade == null) {

            fidelidade =
                    FidelidadeCliente.builder()
                    .usuario(usuario)
                    .saldoPontos(0)
                    .build();

            fidelidade =
                    fidelidadeRepository.save(
                            fidelidade);
        }

        fidelidade.setSaldoPontos(
                fidelidade.getSaldoPontos()
                + pontos);

        fidelidadeRepository.save(
                fidelidade);

        MovimentacaoPontos mov =
                MovimentacaoPontos.builder()
                .fidelidadeCliente(fidelidade)
                .quantidade(pontos)
                .tipo(
                        TipoMovimentacaoPontos.CREDITO)
                .build();

        movimentacaoRepository.save(mov);
    }

    public FidelidadeResponse consultar(
            Long usuarioId) {

        FidelidadeCliente fidelidade =
                fidelidadeRepository
                .findByUsuarioId(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cliente sem fidelidade"));

        FidelidadeResponse response =
                new FidelidadeResponse();

        response.setUsuarioId(usuarioId);

        response.setSaldoPontos(
                fidelidade.getSaldoPontos());

        return response;
    }
    
    public FidelidadeResponse resgatar(
            Long usuarioId,
            Integer pontos) {

        FidelidadeCliente fidelidade =
                fidelidadeRepository
                .findByUsuarioId(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cliente sem fidelidade"));

        if (fidelidade.getSaldoPontos() < pontos) {

            throw new RuntimeException(
                    "Saldo insuficiente");
        }

        fidelidade.setSaldoPontos(
                fidelidade.getSaldoPontos()
                - pontos);

        fidelidadeRepository.save(
                fidelidade);

        MovimentacaoPontos mov =
                MovimentacaoPontos.builder()
                .fidelidadeCliente(fidelidade)
                .quantidade(pontos)
                .tipo(
                        TipoMovimentacaoPontos.DEBITO)
                .build();

        movimentacaoRepository.save(mov);

        FidelidadeResponse response =
                new FidelidadeResponse();

        response.setUsuarioId(usuarioId);
        response.setSaldoPontos(
                fidelidade.getSaldoPontos());

        return response;
    }
}