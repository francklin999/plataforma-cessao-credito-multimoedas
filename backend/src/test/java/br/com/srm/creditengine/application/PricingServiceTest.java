package br.com.srm.creditengine.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.srm.creditengine.application.dto.PricingRequest;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.domain.model.ReceivableType;
import br.com.srm.creditengine.domain.pricing.ChequePreDatadoPricingStrategy;
import br.com.srm.creditengine.domain.pricing.DuplicataMercantilPricingStrategy;
import br.com.srm.creditengine.domain.pricing.PricingStrategyResolver;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {
    @Mock private ExchangeRateService exchangeRateService;
    private final PricingStrategyResolver resolver = new PricingStrategyResolver(List.of(
            new DuplicataMercantilPricingStrategy(), new ChequePreDatadoPricingStrategy()));

    @Test
    void shouldCalculateTermFromDueDateAndReturnItInSimulation() {
        PricingService service = new PricingService(resolver, exchangeRateService);
        LocalDate dueDate = LocalDate.now().plusDays(31);
        PricingRequest request = new PricingRequest(new BigDecimal("1000.00"), ReceivableType.DUPLICATA_MERCANTIL,
                dueDate, new BigDecimal("0.01"), Currency.BRL, Currency.USD);
        when(exchangeRateService.latestRate(Currency.BRL, Currency.USD)).thenReturn(new BigDecimal("0.20"));

        var result = service.simulate(request);

        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.termInMonths()).isEqualTo(2);
        assertThat(result.presentValue()).isEqualByComparingTo("190.3629");
    }
}
