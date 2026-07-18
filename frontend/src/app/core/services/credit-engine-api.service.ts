import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ExchangeRateRequest, Page, PricingRequest, PricingResponse, Settlement } from '../models/credit-engine.models';

@Injectable({ providedIn: 'root' })
export class CreditEngineApiService {
  private readonly baseUrl = 'http://localhost:8080/api/v1';

  constructor(private readonly http: HttpClient) { }

  simulate(request: PricingRequest): Observable<PricingResponse> {
    return this.http.post<PricingResponse>(`${this.baseUrl}/pricing/simulations`, request);
  }

  saveExchangeRate(request: ExchangeRateRequest): Observable<unknown> {
    return this.http.post(`${this.baseUrl}/exchange-rates`, request);
  }

  settle(cedent: string, pricing: PricingRequest): Observable<Settlement> {
    return this.http.post<Settlement>(`${this.baseUrl}/settlements`, { cedent, pricing });
  }

  statement(): Observable<Page<Settlement>> {
    return this.http.get<Page<Settlement>>(`${this.baseUrl}/settlements?sort=createdAt,desc`);
  }
}
