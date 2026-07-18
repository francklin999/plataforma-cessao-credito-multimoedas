package br.com.srm.creditengine.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import br.com.srm.creditengine.domain.model.Currency;

public record ExchangeRateResponse(UUID id, Currency sourceCurrency, Currency targetCurrency,
                                   BigDecimal rate, Instant effectiveAt) { }
