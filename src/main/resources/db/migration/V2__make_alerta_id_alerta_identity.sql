-- Oracle 19c does not allow adding IDENTITY to an existing column.
-- Equivalent solution for DB-generated IDs: sequence + trigger.
-- Fixes ORA-01400 when inserting alerts without explicit id.

BEGIN
  EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_ALERTA_ID START WITH 1 INCREMENT BY 1';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -955 THEN
      RAISE;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER TRG_ALERTA_BI
BEFORE INSERT ON ALERTA
FOR EACH ROW
BEGIN
  IF :NEW.ID_ALERTA IS NULL THEN
    :NEW.ID_ALERTA := SEQ_ALERTA_ID.NEXTVAL;
  END IF;
END;
/

