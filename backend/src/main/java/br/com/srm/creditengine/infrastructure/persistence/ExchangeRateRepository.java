package br.com.srm.creditengine.infrastructure.persistence;

import java.util.Optional;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.srm.creditengine.domain.model.Currency;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, UUID> {
    Optional<ExchangeRateEntity> findFirstBySourceCurrencyAndTargetCurrencyAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(
            Currency sourceCurrency, Currency targetCurrency, Instant effectiveAt);
}
