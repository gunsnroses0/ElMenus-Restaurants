CREATE OR REPLACE PROCEDURE public."Restaurant_Delete"(oldusername character varying)
 LANGUAGE plpgsql
AS $procedure$
 DECLARE
 BEGIN
	DELETE FROM public."Restaurants"
	WHERE
	username = oldUsername;
END $procedure$;

