CREATE TABLE IF NOT EXISTS price_tracking (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  shop_id bigint NOT NULL,
  product_id bigint NOT NULL,
  price int NOT NULL,
  registration_date date NOT NULL,
  CONSTRAINT price_tracking_shop_FK FOREIGN KEY (shop_id)
  		REFERENCES shop(id)
  		ON UPDATE CASCADE,
  CONSTRAINT price_tracking_product_FK FOREIGN KEY (product_id)
  		REFERENCES product(id)
  		ON DELETE RESTRICT
  		ON UPDATE CASCADE,
  UNIQUE KEY `unique_set` (`shop_id`,`product_id`,`registration_date`)
) 
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4 
COLLATE=utf8mb4_0900_ai_ci;