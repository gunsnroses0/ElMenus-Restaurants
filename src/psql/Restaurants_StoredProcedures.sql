--ADD Restaurant--
CREATE OR REPLACE FUNCTION Add_Restaurant (_username VARCHAR = NULL, _name varchar(100) = NULL, _hotline VARCHAR (100) = NULL, _delivery_time VARCHAR (100) = NULL, _delivery_fees INT = NULL, _delivery_hours VARCHAR(200) = NULL, _description text = NULL, _picture VARCHAR(100) = NULL)
RETURNS VOID
AS
$BODY$
BEGIN
INSERT INTO Restaurants(
  username,
  name,
  hotline,
  delivery_time,
  delivery_fees,
  delivery_hours,
  description,
  picture
)values(
  _username,
  _name,
  _hotline,
  _delivery_time,
  _delivery_fees,
  _delivery_hours,
  _description,
  _picture
);
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

--GET Restaurant By ID--
CREATE OR REPLACE FUNCTION Get_Restaurant_By_Id (_id INT = NULL)
RETURNS refcursor AS
$BODY$
DECLARE
ref refcursor;
BEGIN
OPEN ref FOR SELECT * FROM Restaurants WHERE id = _id;
RETURN ref;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

--GET Restaurant By Username--
CREATE OR REPLACE FUNCTION Get_Restaurant_By_Username (_username VARCHAR = NULL)
RETURNS refcursor AS
$BODY$
DECLARE
ref refcursor;
BEGIN
OPEN ref FOR SELECT * FROM Restaurants WHERE username = _username;
RETURN ref;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

--UPDATE Restaurant By ID--
CREATE OR REPLACE FUNCTION Update_Restaurant_By_Id (_id INT = NULL, _name varchar(100) = NULL, _hotline VARCHAR (100) = NULL, _delivery_time VARCHAR (100) = NULL, _delivery_fees INT = NULL, _delivery_hours VARCHAR(200) = NULL, _description text = NULL)
RETURNS integer AS
$BODY$
DECLARE
  a_count integer;
BEGIN
UPDATE Restaurants
SET name = _name, hotline = _hotline, delivery_time = _delivery_time, delivery_fees = _delivery_fees, delivery_hours = _delivery_hours, description = _description
WHERE id = _id;
GET DIAGNOSTICS a_count = ROW_COUNT;
RETURN a_count;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

--UPDATE Restaurant By Username--
CREATE OR REPLACE FUNCTION Update_Restaurant_By_Username (_username VARCHAR = NULL, _name varchar(100) = NULL, _hotline VARCHAR (100) = NULL, _delivery_time VARCHAR (100) = NULL, _delivery_fees INT = NULL, _delivery_hours VARCHAR(200) = NULL, _description text = NULL, _picture VARCHAR(100) = NULL)
RETURNS integer AS
$BODY$
DECLARE
  a_count integer;
BEGIN
UPDATE Restaurants
SET name = _name, hotline = _hotline, delivery_time = _delivery_time, delivery_fees = _delivery_fees, delivery_hours = _delivery_hours, description = _description, picture = _picture
WHERE username = _username;
GET DIAGNOSTICS a_count = ROW_COUNT;
RETURN a_count;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

--DELETE Restaurant--
CREATE OR REPLACE FUNCTION Delete_Restaurant (_id INT = NULL)
RETURNS integer AS
$BODY$
DECLARE
  a_count integer;
BEGIN
DELETE FROM Restaurants
WHERE id = _id;
GET DIAGNOSTICS a_count = ROW_COUNT;
RETURN a_count;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;
