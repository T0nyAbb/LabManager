--TABLE: LABORATORIO
CREATE TABLE LABORATORIO(
    ID_LAB INTEGER PRIMARY KEY,
    NOME VARCHAR2(50) NOT NULL,
    DESCRIZIONE VARCHAR2(250) DEFAULT 'Descrizione non presente' NOT NULL 
);
--GENERATORE DI ID--
CREATE SEQUENCE genera_lab_id
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCACHE;

--TRIGGER CHE SETTA IN AUTOMATICO LA CHIAVE PRIMARIA SE NON SPECIFICATA ESPLICITAMENTE IN FASE DI INSERT
CREATE OR REPLACE TRIGGER lab_pk
BEFORE INSERT ON LABORATORIO
FOR EACH ROW
BEGIN
    IF(:NEW.ID_LAB IS NULL) THEN
        :NEW.ID_LAB := genera_lab_id.NEXTVAL;
        END IF;
END lab_pk;
/

--TABLE: SEDE
CREATE TABLE SEDE(
    ID_SEDE INTEGER PRIMARY KEY,
    INDIRIZZO VARCHAR2(100) NOT NULL,
    CAMPO VARCHAR2(50) DEFAULT 'Sconosciuto',
    ANNOFONDAZIONE NUMBER(4, 0),
    ID_LAB INTEGER NOT NULL,
--FOREIGN KEY
    CONSTRAINT sede_fk FOREIGN KEY (ID_LAB) REFERENCES LABORATORIO(ID_LAB) ON DELETE CASCADE
);
--GENERATORE DI ID--
CREATE SEQUENCE genera_sede_id
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCACHE;

--TRIGGER CHE SETTA IN AUTOMATICO LA CHIAVE PRIMARIA SE NON SPECIFICATA ESPLICITAMENTE IN FASE DI INSERT
CREATE OR REPLACE TRIGGER sede_pk
BEFORE INSERT ON SEDE
FOR EACH ROW
BEGIN
    IF(:NEW.ID_SEDE IS NULL) THEN
        :NEW.ID_SEDE := genera_sede_id.NEXTVAL;
        END IF;
END sede_pk;
/  

--TABLE: POSTAZIONE
CREATE TABLE POSTAZIONE(
    ID_POSTAZIONE INTEGER PRIMARY KEY,
    NUMERO INTEGER NOT NULL,
    ID_SEDE INTEGER NOT NULL,
--FOREIGN KEY    
    CONSTRAINT postazione_fk FOREIGN KEY (ID_SEDE) REFERENCES SEDE(ID_SEDE) ON DELETE CASCADE,
--VINCOLO DI UNICITA' DEL NUMERO DELLA POSTAZIONE PER SEDE
    CONSTRAINT one_number UNIQUE (NUMERO, ID_SEDE)
);      
--GENERATORE DI ID--
CREATE SEQUENCE genera_post_id
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCACHE;

--TRIGGER CHE SETTA IN AUTOMATICO LA CHIAVE PRIMARIA SE NON SPECIFICATA ESPLICITAMENTE IN FASE DI INSERT
CREATE OR REPLACE TRIGGER post_pk
BEFORE INSERT ON POSTAZIONE
FOR EACH ROW
BEGIN
    IF(:NEW.ID_POSTAZIONE IS NULL) THEN
        :NEW.ID_POSTAZIONE := genera_post_id.NEXTVAL;
        END IF;
END post_pk;
/

--TABLE: STRUMENTO
CREATE TABLE STRUMENTO(
    ID_STRUMENTO INTEGER PRIMARY KEY,
    DESCRIZIONE VARCHAR2(75) NOT NULL,
    SCHEDATECNICA VARCHAR2(250) DEFAULT 'Scheda tecnica non presente' NOT NULL,
    ID_POSTAZIONE INTEGER NOT NULL,
--FOREIGN KEY
    CONSTRAINT strumento_fk FOREIGN KEY (ID_POSTAZIONE) REFERENCES POSTAZIONE(ID_POSTAZIONE) ON DELETE CASCADE
);
--GENERATORE DI ID--
CREATE SEQUENCE genera_strumento_id
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCACHE;

--TRIGGER CHE SETTA IN AUTOMATICO LA CHIAVE PRIMARIA SE NON SPECIFICATA ESPLICITAMENTE IN FASE DI INSERT
CREATE OR REPLACE TRIGGER strumento_pk
BEFORE INSERT ON STRUMENTO
FOR EACH ROW
BEGIN
    IF(:NEW.ID_STRUMENTO IS NULL) THEN
        :NEW.ID_STRUMENTO := genera_strumento_id.NEXTVAL;
        END IF;
END strumento_pk;
/

--TABLE: RESPONSABILE
CREATE TABLE RESPONSABILE(
    MATRICOLA CHAR(10) PRIMARY KEY,
    NOME VARCHAR2(50) NOT NULL,
    COGNOME VARCHAR2(50) NOT NULL,
    DATANASCITA DATE NOT NULL,
    CODICEFISCALE CHAR(16)NOT NULL,
    INDIRIZZO VARCHAR(100) NOT NULL,
    TELEFONO1 CHAR(10) NOT NULL,
    TELEFONO2 CHAR(10) DEFAULT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    ID_SEDE INTEGER NOT NULL,
--FOREIGN KEY
    CONSTRAINT resp_fk FOREIGN KEY (ID_SEDE) REFERENCES SEDE(ID_SEDE),
--VINCOLO VALID_PHONE
    CONSTRAINT valid_phone1 CHECK (REGEXP_LIKE(TELEFONO1, '^[0-9]{10}$')),
    CONSTRAINT valid_phone2 CHECK (REGEXP_LIKE(TELEFONO2, '^[0-9]{10}$')),
--VINCOLO VALID_CF
    CONSTRAINT valid_cf CHECK (REGEXP_LIKE(CODICEFISCALE, '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$')),
--VINCOLO VALID_EMAIL
    CONSTRAINT valid_mail CHECK (EMAIL LIKE '_%@_%.__%'),
--VINCOLO ONE_RESP
    CONSTRAINT one_resp UNIQUE (ID_SEDE)
);

--TABLE: TECNICO
CREATE TABLE TECNICO(
    MATRICOLA CHAR(10) PRIMARY KEY,
    NOME VARCHAR2(50) NOT NULL,
    COGNOME VARCHAR2(50) NOT NULL,
    DATANASCITA DATE NOT NULL,
    CODICEFISCALE CHAR(16)NOT NULL,
    INDIRIZZO VARCHAR(100) NOT NULL,
    TELEFONO1 CHAR(10) NOT NULL,
    TELEFONO2 CHAR(10) DEFAULT NULL,
    EMAIL VARCHAR(75) NOT NULL,
    ID_SEDE INTEGER NOT NULL,
--FOREIGN KEY
    CONSTRAINT tech_fk FOREIGN KEY (ID_SEDE) REFERENCES SEDE(ID_SEDE),
--VINCOLO VALID_PHONE
    CONSTRAINT tech_valid_phone1 CHECK (REGEXP_LIKE(TELEFONO1, '^[0-9]{10}$')),
    CONSTRAINT tech_valid_phone2 CHECK (REGEXP_LIKE(TELEFONO2, '^[0-9]{10}$')),
--VINCOLO VALID_CF
    CONSTRAINT tech_valid_cf CHECK (REGEXP_LIKE(CODICEFISCALE, '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$')),
--VINCOLO VALID_EMAIL
    CONSTRAINT tech_valid_mail CHECK (EMAIL LIKE '_%@_%.__%')
);

--TABLE: UTENTE
CREATE TABLE UTENTE(
    EMAIL VARCHAR(75) NOT NULL UNIQUE,
    USERNAME VARCHAR(50) PRIMARY KEY,
    USR_PASSWORD RAW(200) NOT NULL,
--VINCOLO VALID_EMAIL
    CONSTRAINT usr_valid_mail CHECK (EMAIL LIKE '_%@_%.__%'),
--VINCOLO VALID_USERNAME
    CONSTRAINT valid_username CHECK (REGEXP_LIKE(USERNAME, '\w{5,}$'))
);

--TABLE: PRENOTAZIONE
CREATE TABLE PRENOTAZIONE(
    ID_PRENOTAZIONE INTEGER PRIMARY KEY,
    DATAPRENOTAZIONE TIMESTAMP NOT NULL,
    DURATA NUMBER NOT NULL,
    DATAINIZIO DATE NOT NULL,
    DATAFINE DATE NOT NULL,
    ID_STRUMENTO INTEGER NOT NULL,
    USERNAME VARCHAR(50) NOT NULL,
--FOREIGN KEY
    CONSTRAINT prenotazione1_fk FOREIGN KEY (ID_STRUMENTO) REFERENCES STRUMENTO(ID_STRUMENTO),
    CONSTRAINT prenotazione2_fk FOREIGN KEY (USERNAME) REFERENCES UTENTE(USERNAME)
);
    
--GENERATORE DI ID--
CREATE SEQUENCE genera_prenotazione_id
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  NOCACHE;   

--TRIGGER CHE SETTA IN AUTOMATICO LA CHIAVE PRIMARIA E LA DATAPRENOTAZIONE SE NON SPECIFICATE ESPLICITAMENTE IN FASE DI INSERT E INFINE CALCOLA LA DATAFINE   
CREATE OR REPLACE TRIGGER prenotazione_pk
BEFORE INSERT ON PRENOTAZIONE
FOR EACH ROW
DECLARE
dataf DATE;
BEGIN
    IF(:NEW.ID_PRENOTAZIONE IS NULL) 
        THEN
        :NEW.ID_PRENOTAZIONE := genera_prenotazione_id.NEXTVAL;
        END IF;
    IF(:NEW.DATAPRENOTAZIONE IS NULL)
        THEN
        SELECT CURRENT_TIMESTAMP INTO :NEW.DATAPRENOTAZIONE FROM DUAL;
        END IF;
    IF(:NEW.DATAFINE IS NULL) 
        THEN
        SELECT :NEW.DATAINIZIO + INTERVAL '1' MINUTE * :NEW.DURATA INTO dataf FROM DUAL;
        :NEW.DATAFINE := dataf;
    END IF;
END PRENOTAZIONE_pk;
/

--TRIGGER CHE IMPLEMENTA IL VINCOLO MAX_TIME
CREATE OR REPLACE TRIGGER max_time
BEFORE INSERT ON PRENOTAZIONE
FOR EACH ROW 
WHEN(NEW.DURATA>60 OR NEW.DURATA<0)
DECLARE tempo_non_valido EXCEPTION;
BEGIN
    
    RAISE tempo_non_valido;
    EXCEPTION 
    WHEN tempo_non_valido THEN
    RAISE_APPLICATION_ERROR(-20000, 'Tempo non valido');
END max_time;
/

--TRIGGER CHE IMPLEMENTA IL VINCOLO DELETE_OR_MODIFY
CREATE OR REPLACE TRIGGER delete_or_modify
BEFORE DELETE OR UPDATE ON PRENOTAZIONE
FOR EACH ROW
DECLARE
curr_date DATE;
modifica_non_valida EXCEPTION;
elim_non_valida EXCEPTION;
BEGIN
    SELECT SYSDATE INTO curr_date FROM DUAL;
    IF DELETING
    THEN
        IF(:OLD.DATAINIZIO<curr_date)
            THEN
                RAISE elim_non_valida;
        END IF;
    END IF;
    IF UPDATING
    THEN
        IF(:OLD.DATAINIZIO<curr_date)
            THEN
                RAISE modifica_non_valida;
        END IF;
    END IF;
    EXCEPTION
    WHEN modifica_non_valida THEN
    RAISE_APPLICATION_ERROR(-20001, 'Impossibile modificare prenotazione passata');
    WHEN elim_non_valida THEN
    RAISE_APPLICATION_ERROR(-20002, 'Impossibile eliminare prenotazione passata');
END delete_or_modify;
/

--TRIGGER CHE IMPLEMENTA IL VINCOLO VALID_PR_DATE
CREATE OR REPLACE TRIGGER valid_pr_date
BEFORE INSERT ON PRENOTAZIONE
FOR EACH ROW
DECLARE
curr_date DATE;
data_non_valida EXCEPTION;
BEGIN
    SELECT SYSDATE INTO curr_date FROM DUAL;
    IF(:NEW.DATAINIZIO<curr_date OR :NEW.DATAFINE<:NEW.DATAINIZIO)
        THEN
            RAISE data_non_valida;
    END IF;
    EXCEPTION
    WHEN data_non_valida THEN
    DBMS_OUTPUT.PUT_LINE('Data non valida');
END valid_pr_date;
/

--FUNZIONE CHE CRITTOGRAFA LA PASSWORD UTILIZZANDO L'ALGORITMO AES128
CREATE OR REPLACE FUNCTION encrypt_pwd(pwd VARCHAR2)
RETURN RAW AS
chiave RAW(16) := '1234567890123456';
modulo NUMBER := DBMS_CRYPTO.ENCRYPT_AES128 + DBMS_CRYPTO.CHAIN_CBC + DBMS_CRYPTO.PAD_PKCS5;
encrypted_raw RAW(2000);
l_return RAW(2000);
BEGIN
    encrypted_raw := DBMS_CRYPTO.ENCRYPT(UTL_I18N.STRING_TO_RAW(pwd, 'AL32UTF8'), modulo, UTL_I18N.STRING_TO_RAW(chiave, 'AL32UTF8'));
    DBMS_OUTPUT.PUT_LINE('Password crittografata:' || encrypted_raw);
    RETURN encrypted_raw;
END encrypt_pwd;
/
--FUNZIONE CHE DECRIPTA LA PASSWORD
CREATE OR REPLACE FUNCTION decrypt_pwd(pwd RAW)
RETURN VARCHAR2 AS
chiave RAW(16) := '1234567890123456';
modulo NUMBER := DBMS_CRYPTO.ENCRYPT_AES128 + DBMS_CRYPTO.CHAIN_CBC + DBMS_CRYPTO.PAD_PKCS5;
decrypted_raw RAW(2000);
decrypted_pwd VARCHAR2(2000);
BEGIN
    decrypted_raw := DBMS_CRYPTO.DECRYPT (pwd, modulo, UTL_I18N.STRING_TO_RAW(chiave, 'AL32UTF8'));
    decrypted_pwd :=  UTL_RAW.CAST_TO_VARCHAR2(decrypted_raw);
    DBMS_OUTPUT.PUT_LINE ('Password decriptata:' || decrypted_pwd);
    RETURN decrypted_pwd;
END decrypt_pwd;
/

--FUNZIONE CHE CONTROLLA LE CREDENZIALI DURANTE IL LOGIN
CREATE OR REPLACE FUNCTION autenticazione(usrname UTENTE.USERNAME%TYPE, pwd VARCHAR2)
RETURN BOOLEAN AS
tmp UTENTE.USERNAME%TYPE;
BEGIN
SELECT USERNAME INTO tmp
FROM UTENTE
WHERE UPPER(usrname) = UTENTE.USERNAME AND decrypt_pwd(UTENTE.USR_PASSWORD) = LTRIM(RTRIM(pwd));
RETURN TRUE;
EXCEPTION
WHEN NO_DATA_FOUND THEN
RETURN FALSE;
END autenticazione;
/

--FUNZIONE CHE CONTROLLA SE LA PASSWORD RISPETTA IL VINCOLO VALID_PW
CREATE OR REPLACE FUNCTION controlla_pw (pw VARCHAR2)
RETURN BOOLEAN AS
BEGIN
    IF(REGEXP_INSTR(pw, '[[:digit:]]')>=1 AND REGEXP_LIKE(pw, '\w{5,}') AND NOT REGEXP_LIKE(pw, '[[:blank:]]'))
    THEN
    RETURN TRUE;
    ELSE
    RETURN FALSE;
    END IF;
END controlla_pw;
/


--TRIGGER CHE IMPLEMENTA IL VINCOLO VALID_PW ED ESEGUE LA CRITTOGRAFIA DELLA PASSWORD
CREATE OR REPLACE TRIGGER inserisci_pw
BEFORE INSERT ON UTENTE
FOR EACH ROW
DECLARE
password_non_valida EXCEPTION;
psswd VARCHAR2(1000);
BEGIN
    psswd := UTL_RAW.CAST_TO_VARCHAR2(:NEW.USR_PASSWORD);
    IF(controlla_pw(psswd))
    THEN
    :NEW.USR_PASSWORD := encrypt_pwd(psswd);
    ELSE
    RAISE password_non_valida;
    END IF;
    EXCEPTION
    WHEN password_non_valida THEN
    RAISE_APPLICATION_ERROR(-20003, 'La password non rispetta i criteri');
END inserisci_pw;
/

CREATE OR REPLACE TRIGGER exclusive_pr
BEFORE INSERT ON PRENOTAZIONE
FOR EACH ROW
DECLARE
strumento_prenotato EXCEPTION;
CURSOR curr IS
SELECT DATAINIZIO,DATAFINE
FROM PRENOTAZIONE
ORDER BY DATAINIZIO ASC;
c_date curr%ROWTYPE;
BEGIN
OPEN curr;
LOOP
    FETCH curr INTO c_date;
    EXIT WHEN curr%NOTFOUND;
    IF(:NEW.DATAINIZIO>=c_date.DATAINIZIO AND :NEW.DATAINIZIO<=c_date.DATAFINE OR :NEW.DATAFINE>=c_date.DATAINIZIO AND :NEW.DATAFINE<=c_date.DATAFINE)
        THEN
        RAISE strumento_prenotato;
        END IF;
    END LOOP;
CLOSE curr;
EXCEPTION
    WHEN strumento_prenotato THEN
    RAISE_APPLICATION_ERROR(-20004, 'Strumento gi� prenotato');
END exclusive_pr;
/

ALTER SESSION SET NLS_DATE_FORMAT = 'DD/MM/YYYY hh24:mi';

COMMIT;







            

        
    


