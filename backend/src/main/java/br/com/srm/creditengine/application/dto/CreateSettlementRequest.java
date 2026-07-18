package br.com.srm.creditengine.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.Valid;

public record CreateSettlementRequest(@NotBlank String cedent, @Valid PricingRequest pricing) { }
