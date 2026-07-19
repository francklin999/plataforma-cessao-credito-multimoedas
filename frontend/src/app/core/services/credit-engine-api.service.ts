import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ExchangeRateRequest, Page, PricingRequest, PricingResponse, Settlement, StatementFilters } from '../models/credit-engine.models';

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

  statement(filters: StatementFilters): Observable<Page<Settlement>> {
    let params = new HttpParams().set('page', filters.page).set('size', filters.size).set('sort', 'createdAt,desc');
    if (filters.cedent) params = params.set('cedent', filters.cedent);
    if (filters.currency) params = params.set('currency', filters.currency);
    if (filters.from) params = params.set('from', `${filters.from}T00:00:00Z`);
    if (filters.to) params = params.set('to', `${filters.to}T23:59:59Z`);
    return this.http.get<Page<Settlement>>(`${this.baseUrl}/settlements`, { params });
  }
}
