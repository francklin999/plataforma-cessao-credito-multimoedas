import { Component, Input } from '@angular/core';
import { Settlement } from '../../core/models/credit-engine.models';

@Component({
  selector: 'app-settlement-statement',
  templateUrl: './settlement-statement.component.html',
  styleUrls: ['./settlement-statement.component.scss']
})
export class SettlementStatementComponent {
  @Input() settlements: Settlement[] = [];
}
