package br.com.srm.creditengine.application.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.domain.model.ReceivableType;

public record PricingRequest(
        @NotNull @DecimalMin(value = "0.01") BigDecimal faceValue,
        @NotNull ReceivableType receivableType,
        @NotNull @Min(0) @Max(360) Integer termInMonths,
        @NotNull @DecimalMin(value = "0.0") BigDecimal baseRate,
        @NotNull Currency assetCurrency,
        @NotNull Currency paymentCurrency) { }
