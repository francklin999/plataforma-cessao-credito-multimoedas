CREATE TABLE exchange_rates (
    id UUID PRIMARY KEY,
    source_currency VARCHAR(3) NOT NULL,
    target_currency VARCHAR(3) NOT NULL,
    rate NUMERIC(19,8) NOT NULL CHECK (rate > 0),
    effective_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version BIGINT
);
CREATE INDEX idx_exchange_rates_pair_effective_at ON exchange_rates (source_currency, target_currency, effective_at DESC);

CREATE TABLE settlements (
    id UUID PRIMARY KEY,
    cedent VARCHAR(120) NOT NULL,
    receivable_type VARCHAR(40) NOT NULL,
    face_value NUMERIC(19,4) NOT NULL CHECK (face_value > 0),
    term_in_months INTEGER NOT NULL CHECK (term_in_months >= 0),
    asset_currency VARCHAR(3) NOT NULL,
    payment_currency VARCHAR(3) NOT NULL,
    base_rate NUMERIC(10,8) NOT NULL CHECK (base_rate >= 0),
    applied_spread NUMERIC(10,8) NOT NULL,
    exchange_rate NUMERIC(19,8),
    present_value NUMERIC(19,4) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE INDEX idx_settlements_report ON settlements (created_at DESC, cedent, payment_currency);
