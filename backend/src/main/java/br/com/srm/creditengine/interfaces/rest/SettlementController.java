package br.com.srm.creditengine.interfaces.rest;

import java.time.Instant;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.srm.creditengine.application.SettlementService;
import br.com.srm.creditengine.application.dto.CreateSettlementRequest;
import br.com.srm.creditengine.application.dto.SettlementResponse;
import br.com.srm.creditengine.domain.model.Currency;

@RestController
@RequestMapping("/api/v1/settlements")
public class SettlementController {
    private final SettlementService service;
    public SettlementController(SettlementService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SettlementResponse create(@Valid @RequestBody CreateSettlementRequest request) { return service.create(request); }

    @GetMapping
    public Page<SettlementResponse> statement(@RequestParam(required = false) Instant from,
                                               @RequestParam(required = false) Instant to,
                                               @RequestParam(required = false) String cedent,
                                               @RequestParam(required = false) Currency currency,
                                               @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return service.statement(from, to, cedent, currency, pageable);
    }
}
