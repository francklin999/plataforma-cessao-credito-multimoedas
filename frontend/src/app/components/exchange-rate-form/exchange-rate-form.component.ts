import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ExchangeRateRequest } from '../../core/models/credit-engine.models';

@Component({
  selector: 'app-exchange-rate-form',
  templateUrl: './exchange-rate-form.component.html',
  styleUrls: ['./exchange-rate-form.component.scss']
})
export class ExchangeRateFormComponent {
  @Input() loading = false;
  @Output() save = new EventEmitter<ExchangeRateRequest>();

  form: ExchangeRateRequest = { sourceCurrency: 'BRL', targetCurrency: 'USD', rate: 0.20 };
  get isInvalid(): boolean { return this.form.rate <= 0 || this.form.sourceCurrency === this.form.targetCurrency; }
  submit(): void { if (!this.isInvalid) this.save.emit({ ...this.form }); }
}
