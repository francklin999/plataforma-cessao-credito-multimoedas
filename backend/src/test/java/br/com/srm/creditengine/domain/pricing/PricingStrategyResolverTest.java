package br.com.srm.creditengine.domain.pricing;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import br.com.srm.creditengine.domain.model.ReceivableType;

class PricingStrategyResolverTest {
    private final PricingStrategyResolver resolver = new PricingStrategyResolver(List.of(
            new DuplicataMercantilPricingStrategy(), new ChequePreDatadoPricingStrategy()));

    @Test
    void shouldApplyDuplicataSpread() {
        BigDecimal value = resolver.calculatePresentValue(new BigDecimal("1000.00"), new BigDecimal("0.01"),
                ReceivableType.DUPLICATA_MERCANTIL, 1);
        assertThat(value).isEqualByComparingTo("975.6098");
    }

    @Test
    void shouldApplyHigherSpreadForCheque() {
        BigDecimal value = resolver.calculatePresentValue(new BigDecimal("1000.00"), new BigDecimal("0.01"),
                ReceivableType.CHEQUE_PRE_DATADO, 1);
        assertThat(value).isEqualByComparingTo("966.1836");
    }
}
