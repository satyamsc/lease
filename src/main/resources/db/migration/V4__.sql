
ALTER TABLE vehicle ADD CONSTRAINT vehicle_unique_combination
UNIQUE (brand_id, model_id, modelYear, vin);