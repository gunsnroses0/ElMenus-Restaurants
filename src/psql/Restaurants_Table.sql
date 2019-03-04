	CREATE TABLE Restaurants
	(
	id BIGSERIAL PRIMARY KEY NOT NULL,
	username VARCHAR REFERENCES Users (username) ON DELETE CASCADE,
	name VARCHAR (100) NOT NULL UNIQUE,
	hotline VARCHAR (100),
	delivery_time VARCHAR (100),
	delivery_fees INT,
	delivery_hours VARCHAR(200),
	description text
	);
