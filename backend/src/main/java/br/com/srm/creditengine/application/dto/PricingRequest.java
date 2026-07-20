package br.com.srm.creditengine.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.domain.model.ReceivableType;

public record PricingRequest(
        @NotNull @DecimalMin(value = "0.01") BigDecimal faceValue,
        @NotNull ReceivableType receivableType,
        @NotNull @FutureOrPresent LocalDate dueDate,
        @NotNull @DecimalMin(value = "0.0") BigDecimal baseRate,
        @NotNull Currency assetCurrency,
        @NotNull Currency paymentCurrency) { }
