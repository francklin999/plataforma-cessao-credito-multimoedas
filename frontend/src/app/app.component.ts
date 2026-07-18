import { Component, OnInit } from '@angular/core';
import { CreditEngineApiService } from './core/services/credit-engine-api.service';
import { ExchangeRateRequest, PricingRequest, PricingResponse, Settlement, SettlementFormValue } from './core/models/credit-engine.models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  simulation?: PricingResponse;
  settlements: Settlement[] = [];
  error = '';
  loading = false;
  rateLoading = false;
  rateMessage = '';

  constructor(private readonly api: CreditEngineApiService) { }

  ngOnInit(): void { this.loadStatement(); }

  simulate(request: PricingRequest): void {
    this.error = ''; this.loading = true;
    this.api.simulate(request).subscribe({
      next: result => { this.simulation = result; this.loading = false; },
      error: err => { this.error = err.error?.message ?? 'Não foi possível simular a operação.'; this.loading = false; }
    });
  }

  settle(formValue: SettlementFormValue): void {
    this.error = ''; this.loading = true;
    this.api.settle(formValue.cedent, formValue.pricing).subscribe({
      next: () => { this.loading = false; this.loadStatement(); },
      error: err => { this.error = err.error?.message ?? 'Não foi possível liquidar a operação.'; this.loading = false; }
    });
  }

  saveExchangeRate(request: ExchangeRateRequest): void {
    this.rateMessage = ''; this.rateLoading = true;
    this.api.saveExchangeRate(request).subscribe({
      next: () => { this.rateMessage = 'Taxa de câmbio salva com sucesso.'; this.rateLoading = false; },
      error: err => { this.rateMessage = err.error?.message ?? 'Não foi possível salvar a taxa de câmbio.'; this.rateLoading = false; }
    });
  }

  private loadStatement(): void {
    this.api.statement().subscribe({ next: page => this.settlements = page.content });
  }
}
