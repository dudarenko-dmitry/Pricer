CREATE TABLE IF NOT EXISTS category  (
	id bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name varchar(50) NOT NULL UNIQUE 
	)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
