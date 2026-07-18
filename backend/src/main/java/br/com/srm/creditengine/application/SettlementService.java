package br.com.srm.creditengine.application;

import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.srm.creditengine.application.dto.CreateSettlementRequest;
import br.com.srm.creditengine.application.dto.PricingResponse;
import br.com.srm.creditengine.application.dto.SettlementResponse;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.infrastructure.persistence.SettlementEntity;
import br.com.srm.creditengine.infrastructure.persistence.SettlementRepository;

@Service
public class SettlementService {
    private final SettlementRepository repository;
    private final PricingService pricingService;
    public SettlementService(SettlementRepository repository, PricingService pricingService) {
        this.repository = repository; this.pricingService = pricingService;
    }

    @Transactional
    public SettlementResponse create(CreateSettlementRequest request) {
        PricingResponse price = pricingService.simulate(request.pricing());
        var input = request.pricing();
        SettlementEntity entity = repository.save(new SettlementEntity(request.cedent().trim(), input.receivableType(),
                input.faceValue(), input.dueDate(), price.termInMonths(), input.assetCurrency(), input.paymentCurrency(), input.baseRate(),
                price.appliedSpread(), price.exchangeRate(), price.presentValue()));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<SettlementResponse> statement(Instant from, Instant to, String cedent, Currency currency, Pageable pageable) {
        Specification<SettlementEntity> specification = Specification.where(null);
        if (from != null) specification = specification.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), from));
        if (to != null) specification = specification.and((root, query, cb) -> cb.lessThan(root.get("createdAt"), to));
        if (cedent != null && !cedent.isBlank()) specification = specification.and((root, query, cb) -> cb.equal(root.get("cedent"), cedent.trim()));
        if (currency != null) specification = specification.and((root, query, cb) -> cb.equal(root.get("paymentCurrency"), currency));
        return repository.findAll(specification, pageable).map(this::toResponse);
    }

    private SettlementResponse toResponse(SettlementEntity entity) {
        return new SettlementResponse(entity.getId(), entity.getCedent(), entity.getReceivableType(), entity.getFaceValue(), entity.getDueDate(),
                entity.getTermInMonths(), entity.getAssetCurrency(), entity.getPaymentCurrency(), entity.getBaseRate(),
                entity.getAppliedSpread(), entity.getExchangeRate(), entity.getPresentValue(), entity.getCreatedAt());
    }
}
