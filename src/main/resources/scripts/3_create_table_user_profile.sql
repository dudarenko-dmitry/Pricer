CREATE TABLE IF NOT EXISTS user_profile (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  UNIQUE KEY name (first_name, last_name),
  CONSTRAINT users_user_profile_FK FOREIGN KEY (id) 
  	REFERENCES users(id)
  	ON DELETE CASCADE ON UPDATE CASCADE
) 
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4 
COLLATE=utf8mb4_0900_ai_ci;