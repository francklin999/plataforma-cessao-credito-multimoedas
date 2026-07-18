package br.com.srm.creditengine.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import br.com.srm.creditengine.domain.model.Currency;

public record UpsertExchangeRateRequest(
        @NotNull Currency sourceCurrency,
        @NotNull Currency targetCurrency,
        @NotNull @DecimalMin(value = "0.00000001") BigDecimal rate,
        Instant effectiveAt) { }
