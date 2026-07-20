import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Currency, PricingRequest, ReceivableType, SettlementFormValue } from '../../core/models/credit-engine.models';

interface PricingFormState {
  faceValue: number | null;
  receivableType: ReceivableType | '';
  dueDate: string;
  baseRate: number | null;
  assetCurrency: Currency | '';
  paymentCurrency: Currency | '';
}

@Component({
  selector: 'app-pricing-form',
  templateUrl: './pricing-form.component.html',
  styleUrls: ['./pricing-form.component.scss']
})
export class PricingFormComponent {
  @Input() loading = false;
  @Output() simulate = new EventEmitter<PricingRequest>();
  @Output() settle = new EventEmitter<SettlementFormValue>();

  cedent = '';
  readonly minDueDate = new Date().toISOString().slice(0, 10);
  form: PricingFormState = {
    faceValue: null,
    receivableType: '',
    dueDate: '',
    baseRate: null,
    assetCurrency: '',
    paymentCurrency: ''
  };
  private simulationTimer?: ReturnType<typeof setTimeout>;

  get hasInvalidDueDate(): boolean { return !!this.form.dueDate && this.form.dueDate < this.minDueDate; }
  get isComplete(): boolean { return this.form.faceValue !== null && this.form.faceValue > 0 && !!this.form.receivableType && !!this.form.dueDate && this.form.baseRate !== null && this.form.baseRate >= 0 && !!this.form.assetCurrency && !!this.form.paymentCurrency; }
  get isInvalid(): boolean { return !this.cedent.trim() || !this.isComplete || this.hasInvalidDueDate; }

  onFormChange(): void {
    clearTimeout(this.simulationTimer);
    if (!this.isComplete || this.hasInvalidDueDate) return;
    this.simulationTimer = setTimeout(() => this.simulate.emit(this.pricingRequest()), 450);
  }

  onSettle(): void { if (!this.isInvalid) this.settle.emit({ cedent: this.cedent.trim(), pricing: this.pricingRequest() }); }
  private pricingRequest(): PricingRequest {
    return {
      faceValue: this.form.faceValue as number,
      receivableType: this.form.receivableType as ReceivableType,
      dueDate: this.form.dueDate,
      baseRate: this.form.baseRate as number,
      assetCurrency: this.form.assetCurrency as Currency,
      paymentCurrency: this.form.paymentCurrency as Currency
    };
  }
}
