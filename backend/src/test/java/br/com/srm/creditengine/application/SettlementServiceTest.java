package br.com.srm.creditengine.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.srm.creditengine.application.dto.CreateSettlementRequest;
import br.com.srm.creditengine.application.dto.PricingRequest;
import br.com.srm.creditengine.application.dto.PricingResponse;
import br.com.srm.creditengine.domain.model.Currency;
import br.com.srm.creditengine.domain.model.ReceivableType;
import br.com.srm.creditengine.infrastructure.persistence.SettlementEntity;
import br.com.srm.creditengine.infrastructure.persistence.SettlementRepository;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {
    @Mock private SettlementRepository repository;
    @Mock private PricingService pricingService;

    @Test void shouldPersistSettlementWithAuditablePricingData() {
        PricingRequest pricing = new PricingRequest(new BigDecimal("1000"), ReceivableType.DUPLICATA_MERCANTIL, LocalDate.now().plusDays(30), new BigDecimal("0.01"), Currency.BRL, Currency.USD);
        when(pricingService.simulate(pricing)).thenReturn(new PricingResponse(ReceivableType.DUPLICATA_MERCANTIL, pricing.dueDate(), new BigDecimal("0.015"), new BigDecimal("975.6098"), Currency.USD, new BigDecimal("0.20"), new BigDecimal("195.1220")));
        when(repository.save(any(SettlementEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        var result = new SettlementService(repository, pricingService).create(new CreateSettlementRequest("Cedente", pricing));
        assertThat(result.presentValue()).isEqualByComparingTo("195.1220");
        assertThat(result.dueDate()).isEqualTo(pricing.dueDate());
        verify(repository).save(any(SettlementEntity.class));
    }

    @Test void shouldNotPersistWhenPricingFails() {
        PricingRequest pricing = new PricingRequest(BigDecimal.TEN, ReceivableType.DUPLICATA_MERCANTIL, LocalDate.now(), BigDecimal.ZERO, Currency.BRL, Currency.BRL);
        when(pricingService.simulate(pricing)).thenThrow(new IllegalStateException("pricing failed"));
        assertThatThrownBy(() -> new SettlementService(repository, pricingService).create(new CreateSettlementRequest("Cedente", pricing))).isInstanceOf(IllegalStateException.class);
        verify(repository, never()).save(any());
    }
}
