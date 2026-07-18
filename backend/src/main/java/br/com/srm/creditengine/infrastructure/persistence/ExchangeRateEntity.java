package br.com.srm.creditengine.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import br.com.srm.creditengine.domain.model.Currency;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRateEntity {
    @Id
    private UUID id = UUID.randomUUID();
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 3)
    private Currency sourceCurrency;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 3)
    private Currency targetCurrency;
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal rate;
    @Column(nullable = false)
    private Instant effectiveAt;
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
    @Version private Long version;

    protected ExchangeRateEntity() { }
    public ExchangeRateEntity(Currency sourceCurrency, Currency targetCurrency, BigDecimal rate, Instant effectiveAt) {
        this.sourceCurrency = sourceCurrency; this.targetCurrency = targetCurrency; this.rate = rate; this.effectiveAt = effectiveAt;
    }
    public UUID getId() { return id; }
    public Currency getSourceCurrency() { return sourceCurrency; }
    public Currency getTargetCurrency() { return targetCurrency; }
    public BigDecimal getRate() { return rate; }
    public Instant getEffectiveAt() { return effectiveAt; }
}
