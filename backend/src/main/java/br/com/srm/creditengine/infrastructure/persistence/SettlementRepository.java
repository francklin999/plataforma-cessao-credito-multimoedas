package br.com.srm.creditengine.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettlementRepository extends JpaRepository<SettlementEntity, UUID>, JpaSpecificationExecutor<SettlementEntity> { }
