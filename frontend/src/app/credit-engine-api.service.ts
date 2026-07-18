import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PricingRequest {
  faceValue: number;
  receivableType: 'DUPLICATA_MERCANTIL' | 'CHEQUE_PRE_DATADO';
  termInMonths: number;
  baseRate: number;
  assetCurrency: 'BRL' | 'USD';
  paymentCurrency: 'BRL' | 'USD';
}

export interface PricingResponse {
  appliedSpread: number;
  presentValueInAssetCurrency: number;
  paymentCurrency: string;
  exchangeRate: number;
  presentValue: number;
}

export interface Settlement {
  id: string;
  cedent: string;
  receivableType: string;
  paymentCurrency: string;
  presentValue: number;
  createdAt: string;
}

interface Page<T> { content: T[]; }

@Injectable({ providedIn: 'root' })
export class CreditEngineApiService {
  private readonly baseUrl = 'http://localhost:8080/api/v1';
  constructor(private readonly http: HttpClient) { }

  simulate(request: PricingRequest): Observable<PricingResponse> {
    return this.http.post<PricingResponse>(`${this.baseUrl}/pricing/simulations`, request);
  }
  settle(cedent: string, pricing: PricingRequest): Observable<Settlement> {
    return this.http.post<Settlement>(`${this.baseUrl}/settlements`, { cedent, pricing });
  }
  statement(): Observable<Page<Settlement>> {
    return this.http.get<Page<Settlement>>(`${this.baseUrl}/settlements?sort=createdAt,desc`);
  }
}
