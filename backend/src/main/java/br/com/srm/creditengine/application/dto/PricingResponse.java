package br.com.srm.creditengine.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.domain.model.ReceivableType;

public record PricingResponse(ReceivableType receivableType, LocalDate dueDate, BigDecimal appliedSpread,
                              BigDecimal presentValueInAssetCurrency, Currency paymentCurrency,
                              BigDecimal exchangeRate, BigDecimal presentValue) { }
