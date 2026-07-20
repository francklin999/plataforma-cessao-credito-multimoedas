package br.com.srm.creditengine.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.infrastructure.persistence.ExchangeRateEntity;
import br.com.srm.creditengine.infrastructure.persistence.ExchangeRateRepository;
import br.com.srm.creditengine.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {
    @Mock private ExchangeRateRepository repository;

    @Test void shouldReturnOneForEqualCurrencies() {
        assertThat(new ExchangeRateService(repository).latestRate(Currency.BRL, Currency.BRL)).isEqualByComparingTo(BigDecimal.ONE);
    }

    @Test void shouldUseLatestEffectiveRateInsteadOfFutureQuote() {
        ExchangeRateEntity effective = new ExchangeRateEntity(Currency.BRL, Currency.USD, new BigDecimal("0.20"), Instant.now().minusSeconds(60));
        when(repository.findFirstBySourceCurrencyAndTargetCurrencyAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(any(), any(), any())).thenReturn(Optional.of(effective));
        assertThat(new ExchangeRateService(repository).latestRate(Currency.BRL, Currency.USD)).isEqualByComparingTo("0.20");
        verify(repository).findFirstBySourceCurrencyAndTargetCurrencyAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(any(), any(), any());
    }

    @Test void shouldRejectMissingQuote() {
        when(repository.findFirstBySourceCurrencyAndTargetCurrencyAndEffectiveAtLessThanEqualOrderByEffectiveAtDesc(any(), any(), any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> new ExchangeRateService(repository).latestRate(Currency.BRL, Currency.USD)).isInstanceOf(ResourceNotFoundException.class);
    }
}
