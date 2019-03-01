CREATE OR REPLACE PROCEDURE public."Restaurant_Update"(oldusername character varying, newname character varying, newhotline character varying, newdelivery_time character varying, newdelivery_fees numeric, newdelivery_hours character varying, newdescription text)
 LANGUAGE plpgsql
AS $procedure$
 DECLARE
 BEGIN
	UPDATE public."Restaurants"
	SET 
	name = newname, 
	hotline = newhotline,
	delivery_time = newdelivery_time,
	delivery_fees = newdelivery_fees,
	delivery_hours = newdelivery_hours,
	description = newdescription
	WHERE
	username = oldUsername;
END $procedure$;

