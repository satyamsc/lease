
ALTER TABLE model
ADD CONSTRAINT uc_model_name_brand_id UNIQUE (name, brand_id);
