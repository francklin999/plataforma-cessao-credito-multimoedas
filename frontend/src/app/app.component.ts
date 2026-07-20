import { Component, OnInit } from '@angular/core';
import { CreditEngineApiService } from './core/services/credit-engine-api.service';
import { ExchangeRateRequest, PricingRequest, PricingResponse, Settlement, SettlementFormValue, StatementFilters } from './core/models/credit-engine.models';

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
  statementPage = 0;
  statementTotalPages = 0;
  statementTotalElements = 0;
  statementLoading = false;
  private statementFilters: StatementFilters = { page: 0, size: 20 };

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
      next: () => { this.loading = false; this.loadStatement(this.statementFilters); },
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

  loadStatement(filters: StatementFilters = this.statementFilters): void {
    this.statementLoading = true; this.statementFilters = filters;
    this.api.statement(filters).subscribe({ next: page => { this.settlements = page.content; this.statementPage = page.number; this.statementTotalPages = page.totalPages; this.statementTotalElements = page.totalElements; this.statementLoading = false; }, error: () => this.statementLoading = false });
  }
}
