CREATE OR REPLACE PROCEDURE public."Add_Restaurant"(username character varying, name character varying, hotline character varying, delivery_time character varying, delivery_fees numeric, delivery_hours character varying, description text)
 -- LANGUAGE plpgsql
AS $procedure$
 DECLARE
 BEGIN
	INSERT INTO public."Restaurants"(username, name,hotline,delivery_time,delivery_fees,delivery_hours,description)
	VALUES
	(username, name,hotline,delivery_time,delivery_fees,delivery_hours,description);
 COMMIT;
END $procedure$;
