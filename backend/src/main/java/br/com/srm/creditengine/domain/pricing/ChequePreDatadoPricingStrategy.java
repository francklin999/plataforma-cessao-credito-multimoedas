package br.com.srm.creditengine.domain.pricing;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import br.com.srm.creditengine.domain.model.ReceivableType;

@Component
public class ChequePreDatadoPricingStrategy implements PricingStrategy {
    private static final BigDecimal SPREAD = new BigDecimal("0.025");

    @Override
    public ReceivableType supportedType() {
        return ReceivableType.CHEQUE_PRE_DATADO;
    }

    @Override
    public BigDecimal monthlySpread() {
        return SPREAD;
    }
}
