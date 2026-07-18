package br.com.srm.creditengine.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.domain.model.ReceivableType;

@Entity
@Table(name = "settlements")
public class SettlementEntity {
    @Id private UUID id = UUID.randomUUID();
    @Column(nullable = false, length = 120) private String cedent;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 40) private ReceivableType receivableType;
    @Column(nullable = false, precision = 19, scale = 4) private BigDecimal faceValue;
    @Column(nullable = false) private Integer termInMonths;
    @Column(nullable = false) private LocalDate dueDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 3) private Currency assetCurrency;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 3) private Currency paymentCurrency;
    @Column(nullable = false, precision = 10, scale = 8) private BigDecimal baseRate;
    @Column(nullable = false, precision = 10, scale = 8) private BigDecimal appliedSpread;
    @Column(precision = 19, scale = 8) private BigDecimal exchangeRate;
    @Column(nullable = false, precision = 19, scale = 4) private BigDecimal presentValue;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    protected SettlementEntity() { }
    public SettlementEntity(String cedent, ReceivableType receivableType, BigDecimal faceValue, LocalDate dueDate, Integer termInMonths,
                            Currency assetCurrency, Currency paymentCurrency, BigDecimal baseRate,
                            BigDecimal appliedSpread, BigDecimal exchangeRate, BigDecimal presentValue) {
        this.cedent = cedent; this.receivableType = receivableType; this.faceValue = faceValue; this.dueDate = dueDate; this.termInMonths = termInMonths;
        this.assetCurrency = assetCurrency; this.paymentCurrency = paymentCurrency; this.baseRate = baseRate;
        this.appliedSpread = appliedSpread; this.exchangeRate = exchangeRate; this.presentValue = presentValue;
    }
    public UUID getId() { return id; }
    public String getCedent() { return cedent; }
    public ReceivableType getReceivableType() { return receivableType; }
    public BigDecimal getFaceValue() { return faceValue; }
    public Integer getTermInMonths() { return termInMonths; }
    public LocalDate getDueDate() { return dueDate; }
    public Currency getAssetCurrency() { return assetCurrency; }
    public Currency getPaymentCurrency() { return paymentCurrency; }
    public BigDecimal getBaseRate() { return baseRate; }
    public BigDecimal getAppliedSpread() { return appliedSpread; }
    public BigDecimal getExchangeRate() { return exchangeRate; }
    public BigDecimal getPresentValue() { return presentValue; }
    public Instant getCreatedAt() { return createdAt; }
}
