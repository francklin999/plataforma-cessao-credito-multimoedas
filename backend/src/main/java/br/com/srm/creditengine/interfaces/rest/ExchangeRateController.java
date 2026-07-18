package br.com.srm.creditengine.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.srm.creditengine.application.ExchangeRateService;
import br.com.srm.creditengine.application.dto.ExchangeRateResponse;
import br.com.srm.creditengine.application.dto.UpsertExchangeRateRequest;

@RestController
@RequestMapping("/api/v1/exchange-rates")
public class ExchangeRateController {
    private final ExchangeRateService service;
    public ExchangeRateController(ExchangeRateService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExchangeRateResponse create(@Valid @RequestBody UpsertExchangeRateRequest request) {
        return service.save(request);
    }
}
