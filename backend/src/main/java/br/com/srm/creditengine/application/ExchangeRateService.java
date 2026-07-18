package br.com.srm.creditengine.application;

import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.srm.creditengine.application.dto.ExchangeRateResponse;
import br.com.srm.creditengine.application.dto.UpsertExchangeRateRequest;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.infrastructure.persistence.ExchangeRateEntity;
import br.com.srm.creditengine.infrastructure.persistence.ExchangeRateRepository;
import br.com.srm.creditengine.shared.exception.BusinessException;
import br.com.srm.creditengine.shared.exception.ResourceNotFoundException;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository repository;
    public ExchangeRateService(ExchangeRateRepository repository) { this.repository = repository; }

    @Transactional
    public ExchangeRateResponse save(UpsertExchangeRateRequest request) {
        if (request.sourceCurrency() == request.targetCurrency()) {
            throw new BusinessException("A moeda de origem deve ser diferente da moeda de destino.");
        }
        ExchangeRateEntity entity = repository.save(new ExchangeRateEntity(request.sourceCurrency(), request.targetCurrency(),
                request.rate(), request.effectiveAt() == null ? Instant.now() : request.effectiveAt()));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public BigDecimal latestRate(Currency source, Currency target) {
        if (source == target) return BigDecimal.ONE;
        return repository.findFirstBySourceCurrencyAndTargetCurrencyOrderByEffectiveAtDesc(source, target)
                .map(ExchangeRateEntity::getRate)
                .orElseThrow(() -> new ResourceNotFoundException("Não há taxa de câmbio vigente para " + source + "/" + target + "."));
    }

    private ExchangeRateResponse toResponse(ExchangeRateEntity entity) {
        return new ExchangeRateResponse(entity.getId(), entity.getSourceCurrency(), entity.getTargetCurrency(), entity.getRate(), entity.getEffectiveAt());
    }
}
