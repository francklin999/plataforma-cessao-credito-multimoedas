import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppHeaderComponent } from './components/app-header/app-header.component';
import { PricingFormComponent } from './components/pricing-form/pricing-form.component';
import { PricingResultComponent } from './components/pricing-result/pricing-result.component';
import { SettlementStatementComponent } from './components/settlement-statement/settlement-statement.component';
import { ExchangeRateFormComponent } from './components/exchange-rate-form/exchange-rate-form.component';

@NgModule({
  declarations: [
    AppComponent,
    AppHeaderComponent,
    PricingFormComponent,
    PricingResultComponent,
    SettlementStatementComponent,
    ExchangeRateFormComponent
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
