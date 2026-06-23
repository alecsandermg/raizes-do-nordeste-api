package com.raizesdonordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.AuditLog;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {

}