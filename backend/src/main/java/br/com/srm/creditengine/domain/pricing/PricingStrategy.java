package br.com.srm.creditengine.domain.pricing;

import java.math.BigDecimal;
import br.com.srm.creditengine.domain.model.ReceivableType;

public interface PricingStrategy {
    ReceivableType supportedType();
    BigDecimal monthlySpread();
}
