import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PricingRequest, SettlementFormValue } from '../../core/models/credit-engine.models';

@Component({
  selector: 'app-pricing-form',
  templateUrl: './pricing-form.component.html',
  styleUrls: ['./pricing-form.component.scss']
})
export class PricingFormComponent {
  @Input() loading = false;
  @Output() simulate = new EventEmitter<PricingRequest>();
  @Output() settle = new EventEmitter<SettlementFormValue>();

  cedent = 'Empresa Exemplo Ltda';
  form: PricingRequest = {
    faceValue: 1000,
    receivableType: 'DUPLICATA_MERCANTIL',
    dueDate: this.defaultDueDate(),
    baseRate: 0.01,
    assetCurrency: 'BRL',
    paymentCurrency: 'USD'
  };

  onSimulate(): void { this.simulate.emit({ ...this.form }); }
  onSettle(): void { this.settle.emit({ cedent: this.cedent.trim(), pricing: { ...this.form } }); }

  private defaultDueDate(): string {
    const dueDate = new Date();
    dueDate.setMonth(dueDate.getMonth() + 1);
    return dueDate.toISOString().slice(0, 10);
  }
}
