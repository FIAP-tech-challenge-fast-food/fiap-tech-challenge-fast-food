-- V7__Add_deleted_column_to_products

ALTER TABLE products ADD deleted boolean default false;
