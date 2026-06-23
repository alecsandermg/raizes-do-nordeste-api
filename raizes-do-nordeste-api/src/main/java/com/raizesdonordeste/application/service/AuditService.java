package com.raizesdonordeste.application.service;

import org.springframework.stereotype.Service;

import com.raizesdonordeste.domain.entity.AuditLog;
import com.raizesdonordeste.infrastructure.repository.AuditLogRepository;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(
            AuditLogRepository auditLogRepository) {

        this.auditLogRepository = auditLogRepository;
    }

    public void registrar(
            String entidade,
            String acao,
            String usuario) {

        AuditLog log = AuditLog.builder()
                .entidade(entidade)
                .acao(acao)
                .usuario(usuario)
                .build();

        auditLogRepository.save(log);
    }
}