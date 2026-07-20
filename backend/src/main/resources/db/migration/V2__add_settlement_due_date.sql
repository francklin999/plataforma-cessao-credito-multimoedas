ALTER TABLE settlements ADD COLUMN due_date DATE;

UPDATE settlements
SET due_date = CURRENT_DATE + (term_in_months * INTERVAL '1 month');

ALTER TABLE settlements ALTER COLUMN due_date SET NOT NULL;
ALTER TABLE settlements DROP COLUMN term_in_months;
