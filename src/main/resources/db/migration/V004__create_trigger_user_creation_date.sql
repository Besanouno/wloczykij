CREATE OR REPLACE FUNCTION add_user_creation_date()
  RETURNS TRIGGER AS $user_creation_date$
BEGIN
  IF NEW.creation_date IS NULL
  THEN
    NEW.creation_date := CURRENT_DATE;
  END IF;
  RETURN NEW;
END;
$user_creation_date$ LANGUAGE plpgsql;

CREATE TRIGGER user_creation_date
BEFORE INSERT ON users
FOR EACH ROW
EXECUTE PROCEDURE add_user_creation_date();