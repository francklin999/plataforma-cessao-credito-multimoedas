import { Component, Input } from '@angular/core';
import { PricingResponse } from '../../core/models/credit-engine.models';

@Component({
  selector: 'app-pricing-result',
  templateUrl: './pricing-result.component.html',
  styleUrls: ['./pricing-result.component.scss']
})
export class PricingResultComponent {
  @Input() simulation?: PricingResponse;
}
