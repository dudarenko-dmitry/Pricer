INSERT INTO category 
	(`name`)
	VALUES 
		('Meat'),
		('Fish'),
		('Milk'),
		('Bread'),
		('Fruits'),
		('Drinks');
	
INSERT INTO shop 
	(`name`, `ciry`, `address`)
	VALUES
		('Achan', 'Warsaw', 'Address1'),
		('Achan', 'Bielostok', 'Address2'),
		('Achan', 'Poznan', 'Address3'),
		('Lidl', 'Berlin', 'Address4'),
		('Lidl', 'Munchen', 'Address5'),
		('Lidl', 'Drezden', 'Address6'),
		('El Corte Ingles', 'Barcelona', 'Address7'),
		('El Corte Ingles', 'Madrid', 'Address8'),
		('El Corte Ingles', 'Lisbon', 'Address9');
		
INSERT INTO product
	(`name`, `category_id`)
	VALUES
		(`product1`, 1),
		(`product2`, 1),
		(`product3`, 1),
		(`product4`, 3),
		(`product5`, 3),
		(`product6`, 6),
		(`product7`, 6),
		(`product8`, 2),
		(`product9`, 4),
		(`product10`, 5),
		(`product11`, 5);
	
INSERT INTO price_tracking
	(`shop_id`, `product_id`, `price`, `registration_date`)
	VALUES
		(1, 2, 200, '2023-01-02'),
		(2, 2, 210, '2023-01-02'),
		(3, 2, 205, '2023-01-02'),
		(4, 2, 220, '2023-01-02'),
		(1, 2, 200, '2023-01-10'),
		(2, 2, 210, '2023-01-10'),
		(3, 2, 205, '2023-01-10'),
		(4, 2, 220, '2023-01-12'),
		(1, 2, 220, '2023-01-10'),
		(2, 2, 213, '2023-01-10'),
		(3, 2, 203, '2023-01-10'),
		(4, 2, 222, '2023-01-12'),
		(1, 2, 200, '2023-01-02'),
		(4, 2, 100, '2023-01-02'),
		(3, 2, 102, '2023-01-02'),
		(6, 2, 110, '2023-01-02'),
		(8, 2, 130, '2023-01-02'),
		(5, 2, 100, '2023-01-11'),
		(1, 2, 102, '2023-01-11'),
		(6, 2, 110, '2023-01-13'),
		(8, 2, 130, '2023-01-15'),		
		(6, 2, 110, '2023-01-22'),
		(8, 2, 130, '2023-01-22'),
		(5, 2, 120, '2023-01-18'),
		(3, 4, 102, '2023-02-11'),
		(6, 4, 200, '2023-02-02'),
		(2, 4, 200, '2023-02-02'),
		(7, 4, 200, '2023-02-02'),
		(3, 4, 113, '2023-02-20'),
		(6, 4, 130, '2023-02-20'),
		(2, 4, 90,	'2023-02-20'),
		(7, 4, 110, '2023-02-20');
	
	INSERT INTO users 
		(`username`, `password`, `enabled`, `role`)
		VALUES 
			('user11', '{bcrypt}$2a$10$7spAIptzG7rTw7WSV6nvwuLA3YWJzVQkvlxQSNQuHUaPG1tg0nlpa', 1, 'REGULAR'),
			('user12', '{bcrypt}$2a$10$wblQjbTa9Gun08MC64QBKu47kSrD/lPSmIB1v/KNFF7klLVxIDh4e', 1, 'REGULAR'),
			('user13', '{bcrypt}$2a$10$bei8fxCS/pWgWm5U3Z3aleXngu.P544K1uk6AuaBu5YlSFEpCdL06', 1, 'REGULAR'),
			('admin2', '{bcrypt}$2a$10$mc2qDEG3KhJ1lW75gFiNrO5rHG3bS58S.71IaaSAGUejSu4uHvvQu', 1, 'REGULAR');
		
	INSERT INTO user_profile 
		(`first_name`, `last_name`)
		VALUES
			('Ivan', 'Ivanov'),
			('Petr', 'Petrov'),
			('John', 'Johnson'),
			('Admin', 'Adminoff');
			
	