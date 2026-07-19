import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Currency, Settlement, StatementFilters } from '../../core/models/credit-engine.models';

@Component({
  selector: 'app-settlement-statement',
  templateUrl: './settlement-statement.component.html',
  styleUrls: ['./settlement-statement.component.scss']
})
export class SettlementStatementComponent {
  @Input() settlements: Settlement[] = [];
  @Input() page = 0;
  @Input() totalPages = 0;
  @Input() totalElements = 0;
  @Input() loading = false;
  @Output() search = new EventEmitter<StatementFilters>();
  filters: Partial<StatementFilters> = { cedent: '', currency: '', from: '', to: '', size: 20 };

  applyFilters(): void { this.search.emit({ ...this.filters, page: 0, size: this.filters.size || 20 }); }
  changePage(page: number): void { this.search.emit({ ...this.filters, page, size: this.filters.size || 20 }); }
}
