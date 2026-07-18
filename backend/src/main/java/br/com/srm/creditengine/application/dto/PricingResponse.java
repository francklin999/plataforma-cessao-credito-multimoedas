package br.com.srm.creditengine.application.dto;

import java.math.BigDecimal;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.domain.model.ReceivableType;

public record PricingResponse(ReceivableType receivableType, BigDecimal appliedSpread,
                              BigDecimal presentValueInAssetCurrency, Currency paymentCurrency,
                              BigDecimal exchangeRate, BigDecimal presentValue) { }
