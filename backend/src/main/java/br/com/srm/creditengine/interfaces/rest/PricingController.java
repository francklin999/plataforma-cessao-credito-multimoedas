package br.com.srm.creditengine.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.srm.creditengine.application.PricingService;
import br.com.srm.creditengine.application.dto.PricingRequest;
import br.com.srm.creditengine.application.dto.PricingResponse;

@RestController
@RequestMapping("/api/v1/pricing")
public class PricingController {
    private final PricingService service;
    public PricingController(PricingService service) { this.service = service; }

    @PostMapping("/simulations")
    public PricingResponse simulate(@Valid @RequestBody PricingRequest request) {
        return service.simulate(request);
    }
}
