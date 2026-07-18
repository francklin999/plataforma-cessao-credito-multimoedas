package br.com.srm.creditengine.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.domain.model.ReceivableType;

public record SettlementResponse(UUID id, String cedent, ReceivableType receivableType,
                                 BigDecimal faceValue, LocalDate dueDate, Integer termInMonths, Currency assetCurrency,
                                 Currency paymentCurrency, BigDecimal baseRate, BigDecimal appliedSpread,
                                 BigDecimal exchangeRate, BigDecimal presentValue, Instant createdAt) { }
