export type Currency = 'BRL' | 'USD';
export type ReceivableType = 'DUPLICATA_MERCANTIL' | 'CHEQUE_PRE_DATADO';

export interface PricingRequest {
  faceValue: number;
  receivableType: ReceivableType;
  dueDate: string;
  baseRate: number;
  assetCurrency: Currency;
  paymentCurrency: Currency;
}

export interface PricingResponse {
  dueDate: string;
  termInMonths: number;
  appliedSpread: number;
  presentValueInAssetCurrency: number;
  paymentCurrency: Currency;
  exchangeRate: number;
  presentValue: number;
}

export interface Settlement {
  id: string;
  cedent: string;
  receivableType: ReceivableType;
  paymentCurrency: Currency;
  presentValue: number;
  createdAt: string;
}

export interface SettlementFormValue {
  cedent: string;
  pricing: PricingRequest;
}

export interface ExchangeRateRequest {
  sourceCurrency: Currency;
  targetCurrency: Currency;
  rate: number;
}

export interface Page<T> {
  content: T[];
}
