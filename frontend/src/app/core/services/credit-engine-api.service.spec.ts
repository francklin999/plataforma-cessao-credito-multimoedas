import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CreditEngineApiService } from './credit-engine-api.service';

describe('CreditEngineApiService', () => {
  let service: CreditEngineApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(CreditEngineApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('deve enviar uma simulação para a API', () => {
    const request = { faceValue: 1000, receivableType: 'DUPLICATA_MERCANTIL' as const, dueDate: '2026-08-18', baseRate: 0.01, assetCurrency: 'BRL' as const, paymentCurrency: 'USD' as const };
    service.simulate(request).subscribe(result => expect(result.presentValue).toBe(195.122));
    const httpRequest = httpMock.expectOne('http://localhost:8080/api/v1/pricing/simulations');
    expect(httpRequest.request.method).toBe('POST');
    expect(httpRequest.request.body).toEqual(request);
    httpRequest.flush({ presentValue: 195.122 });
  });

  it('deve cadastrar a taxa de câmbio', () => {
    service.saveExchangeRate({ sourceCurrency: 'BRL', targetCurrency: 'USD', rate: 0.2 }).subscribe();
    const httpRequest = httpMock.expectOne('http://localhost:8080/api/v1/exchange-rates');
    expect(httpRequest.request.method).toBe('POST');
    httpRequest.flush({});
  });
});
