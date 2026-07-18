import { Component, OnInit } from '@angular/core';
import { CreditEngineApiService, PricingRequest, PricingResponse, Settlement } from './credit-engine-api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  form: PricingRequest = {
    faceValue: 1000, receivableType: 'DUPLICATA_MERCANTIL', termInMonths: 1,
    baseRate: 0.01, assetCurrency: 'BRL', paymentCurrency: 'USD'
  };
  cedent = 'Empresa Exemplo Ltda';
  simulation?: PricingResponse;
  settlements: Settlement[] = [];
  error = '';
  loading = false;

  constructor(private readonly api: CreditEngineApiService) { }

  ngOnInit(): void { this.loadStatement(); }

  simulate(): void {
    this.error = ''; this.loading = true;
    this.api.simulate(this.form).subscribe({
      next: result => { this.simulation = result; this.loading = false; },
      error: err => { this.error = err.error?.message ?? 'Não foi possível simular a operação.'; this.loading = false; }
    });
  }

  settle(): void {
    this.error = ''; this.loading = true;
    this.api.settle(this.cedent, this.form).subscribe({
      next: () => { this.loading = false; this.loadStatement(); },
      error: err => { this.error = err.error?.message ?? 'Não foi possível liquidar a operação.'; this.loading = false; }
    });
  }

  private loadStatement(): void {
    this.api.statement().subscribe({ next: page => this.settlements = page.content });
  }
}
