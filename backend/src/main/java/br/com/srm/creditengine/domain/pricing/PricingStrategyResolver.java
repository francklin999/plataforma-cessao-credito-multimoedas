package br.com.srm.creditengine.domain.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import br.com.srm.creditengine.domain.model.ReceivableType;
import br.com.srm.creditengine.shared.exception.BusinessException;

@Component
public class PricingStrategyResolver {
    private final Map<ReceivableType, PricingStrategy> strategies = new EnumMap<>(ReceivableType.class);

    public PricingStrategyResolver(List<PricingStrategy> strategies) {
        strategies.forEach(strategy -> this.strategies.put(strategy.supportedType(), strategy));
    }

    public BigDecimal calculatePresentValue(BigDecimal faceValue, BigDecimal baseRate,
                                            ReceivableType type, int termInMonths) {
        if (termInMonths < 0) {
            throw new BusinessException("O prazo em meses não pode ser negativo.");
        }
        PricingStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new BusinessException("Não existe estratégia de precificação para o tipo informado.");
        }
        BigDecimal monthlyRate = BigDecimal.ONE.add(baseRate).add(strategy.monthlySpread());
        BigDecimal discountFactor = monthlyRate.pow(termInMonths);
        return faceValue.divide(discountFactor, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal spreadFor(ReceivableType type) {
        PricingStrategy strategy = strategies.get(type);
        if (strategy == null) throw new BusinessException("Tipo de recebível não suportado.");
        return strategy.monthlySpread();
    }
}
