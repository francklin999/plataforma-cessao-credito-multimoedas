package br.com.srm.creditengine.domain.pricing;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import br.com.srm.creditengine.domain.model.ReceivableType;

@Component
public class DuplicataMercantilPricingStrategy implements PricingStrategy {
    private static final BigDecimal SPREAD = new BigDecimal("0.015");

    @Override
    public ReceivableType supportedType() {
        return ReceivableType.DUPLICATA_MERCANTIL;
    }

    @Override
    public BigDecimal monthlySpread() {
        return SPREAD;
    }
}
