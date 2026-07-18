package br.com.srm.creditengine.application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import br.com.srm.creditengine.application.dto.PricingRequest;
import br.com.srm.creditengine.application.dto.PricingResponse;
import br.com.srm.creditengine.domain.pricing.PricingStrategyResolver;

@Service
public class PricingService {
    private final PricingStrategyResolver strategyResolver;
    private final ExchangeRateService exchangeRateService;
    public PricingService(PricingStrategyResolver strategyResolver, ExchangeRateService exchangeRateService) {
        this.strategyResolver = strategyResolver; this.exchangeRateService = exchangeRateService;
    }

    public PricingResponse simulate(PricingRequest request) {
        BigDecimal assetPresentValue = strategyResolver.calculatePresentValue(request.faceValue(), request.baseRate(),
                request.receivableType(), request.termInMonths());
        BigDecimal exchangeRate = exchangeRateService.latestRate(request.assetCurrency(), request.paymentCurrency());
        BigDecimal settlementValue = assetPresentValue.multiply(exchangeRate).setScale(4, RoundingMode.HALF_UP);
        return new PricingResponse(request.receivableType(), strategyResolver.spreadFor(request.receivableType()),
                assetPresentValue, request.paymentCurrency(), exchangeRate, settlementValue);
    }
}
