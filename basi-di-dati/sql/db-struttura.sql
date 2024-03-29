--CREAZIONE SEQUENCE (AUTOINCREMENT PER GENERAZIONE AUTOMATICA DI ID)

--SEQUENCE: GENERA ID PER LABORATORIO
CREATE SEQUENCE genera_id_lab
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  NOCACHE;

--SEQUENCE: GENERA ID PER SEDE
CREATE SEQUENCE genera_id_sede
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  NOCACHE;

--SEQUENCE: GENERA ID PER POSTAZIONE
CREATE SEQUENCE genera_id_post
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  NOCACHE;

--SEQUENCE: GENERA ID PER STRUMENTO
CREATE SEQUENCE genera_id_strum
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  NOCACHE;

--SEQUENCE: GENERA ID PER PRENOTAZIONE
CREATE SEQUENCE genera_id_pren
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  NOCACHE;










--CREAZIONE TABELLE

--TABLE: LABORATORIO
CREATE TABLE LABORATORIO(
    ID_LAB INTEGER DEFAULT ON NULL genera_id_lab.NEXTVAL PRIMARY KEY,
    NOME VARCHAR2(50) NOT NULL,
    ANNOFONDAZIONE NUMBER(4, 0) NOT NULL,
    CAMPO VARCHAR2(50) DEFAULT ON NULL 'Sconosciuto' NOT NULL,
    DESCRIZIONE VARCHAR2(250) DEFAULT ON NULL 'Descrizione non presente' NOT NULL 
);

--TABLE: SEDE
CREATE TABLE SEDE(
    ID_SEDE INTEGER DEFAULT ON NULL genera_id_sede.NEXTVAL PRIMARY KEY,
    INDIRIZZO VARCHAR2(100) NOT NULL,
    ID_LAB INTEGER NOT NULL,
    --FOREIGN KEY
    CONSTRAINT sede_fk FOREIGN KEY (ID_LAB) REFERENCES LABORATORIO(ID_LAB) ON DELETE CASCADE
);

--TABLE: POSTAZIONE
CREATE TABLE POSTAZIONE(
    ID_POSTAZIONE INTEGER DEFAULT ON NULL genera_id_post.NEXTVAL PRIMARY KEY,
    NOME CHAR(2) NOT NULL,
    ID_SEDE INTEGER NOT NULL,
    --FOREIGN KEY    
    CONSTRAINT postazione_fk FOREIGN KEY (ID_SEDE) REFERENCES SEDE(ID_SEDE) ON DELETE CASCADE,
    --VINCOLO UNIQ_POST_NOME
    CONSTRAINT uniq_post_nome UNIQUE (NOME, ID_SEDE),
    --VINCOLO VALID_POST_NOME
    CONSTRAINT valid_post_nome CHECK (REGEXP_LIKE(NOME, '^[A-Z][1-9]$'))
);

--TABLE: STRUMENTO
CREATE TABLE STRUMENTO(
    ID_STRUMENTO INTEGER DEFAULT ON NULL genera_id_strum.NEXTVAL PRIMARY KEY,
    DESCRIZIONE VARCHAR2(75) NOT NULL,
    SCHEDATECNICA VARCHAR2(250) DEFAULT ON NULL'Scheda tecnica non presente' NOT NULL,
    ID_POSTAZIONE INTEGER,
    --FOREIGN KEY
    CONSTRAINT strumento_fk FOREIGN KEY (ID_POSTAZIONE) REFERENCES POSTAZIONE(ID_POSTAZIONE) ON DELETE SET NULL
);

--TABLE: RESPONSABILE
CREATE TABLE RESPONSABILE(
    MATRICOLA CHAR(10) PRIMARY KEY,
    NOME VARCHAR2(50) NOT NULL,
    COGNOME VARCHAR2(50) NOT NULL,
    DATANASCITA DATE NOT NULL,
    CODICEFISCALE CHAR(16) NOT NULL,
    INDIRIZZO VARCHAR(100) NOT NULL,
    TELEFONO1 CHAR(10) NOT NULL,
    TELEFONO2 CHAR(10),
    EMAIL VARCHAR(100) NOT NULL,
    ID_SEDE INTEGER NOT NULL UNIQUE,
    --FOREIGN KEY
    CONSTRAINT responsabile_fk FOREIGN KEY (ID_SEDE) REFERENCES SEDE(ID_SEDE) ON DELETE CASCADE,
    --VINCOLO VALID_MATR
    CONSTRAINT valid_matr_r CHECK (REGEXP_LIKE(MATRICOLA, '^R[0-9]{9}$')),
    --VINCOLO VALID_TEL
    CONSTRAINT valid_tel1_r CHECK (REGEXP_LIKE(TELEFONO1, '^[0-9]{10}$')),
    CONSTRAINT valid_tel2_r CHECK (REGEXP_LIKE(TELEFONO2, '^[0-9]{10}$')),
    --VINCOLO VALID_CF
    CONSTRAINT valid_cf_r CHECK (REGEXP_LIKE(CODICEFISCALE, '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$')),
    --VINCOLO VALID_EMAIL
    CONSTRAINT valid_email_r CHECK (EMAIL LIKE '_%@_%.__%')
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
    TELEFONO2 CHAR(10),
    EMAIL VARCHAR(100) NOT NULL,
    ID_SEDE INTEGER NOT NULL,
    --FOREIGN KEY
    CONSTRAINT tecnico_fk FOREIGN KEY (ID_SEDE) REFERENCES SEDE(ID_SEDE) ON DELETE CASCADE,
    --VINCOLO VALID_MATR
    CONSTRAINT valid_matr_t CHECK (REGEXP_LIKE(MATRICOLA, '^T[0-9]{9}$')),
    --VINCOLO VALID_TEL
    CONSTRAINT valid_tel1_t CHECK (REGEXP_LIKE(TELEFONO1, '^[0-9]{10}$')),
    CONSTRAINT valid_tel2_t CHECK (REGEXP_LIKE(TELEFONO2, '^[0-9]{10}$')),
    --VINCOLO VALID_CF
    CONSTRAINT valid_cf_t CHECK (REGEXP_LIKE(CODICEFISCALE, '^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$')),
    --VINCOLO VALID_EMAIL
    CONSTRAINT valid_mail_t CHECK (EMAIL LIKE '_%@_%.__%')
);

--TABLE: UTENTE
CREATE TABLE UTENTE(
    USERNAME VARCHAR(18) NOT NULL,
    USR_PASSWORD RAW(200) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    --VINCOLO DI CHIAVE PRIMARIA
    CONSTRAINT utente_pk PRIMARY KEY(USERNAME),
    --VINCOLO VALID_EMAIL
    CONSTRAINT valid_email_usr CHECK (EMAIL LIKE '_%@_%.__%'),
    --VINCOLO VALID_USERNAME
    CONSTRAINT valid_username CHECK (REGEXP_LIKE(USERNAME, '[a-zA-Z0-9]{6}.*')),
    --VINCOLO UNNIQ_EMAIL
    CONSTRAINT uniq_email UNIQUE(EMAIL)
);

--TABLE: PRENOTAZIONE
CREATE TABLE PRENOTAZIONE(
    ID_PRENOTAZIONE INTEGER DEFAULT ON NULL genera_id_pren.NEXTVAL PRIMARY KEY,
    DATAINIZIO DATE NOT NULL,
    DURATA INTEGER NOT NULL,
    DATAPRENOTAZIONE DATE DEFAULT ON NULL SYSDATE NOT NULL,
    ID_STRUMENTO INTEGER,
    USERNAME VARCHAR(18),
    --FOREIGN KEY
    CONSTRAINT prenotazione_fk_strumento FOREIGN KEY (ID_STRUMENTO) REFERENCES STRUMENTO(ID_STRUMENTO) ON DELETE SET NULL,
    CONSTRAINT prenotazione_fk_utente FOREIGN KEY (USERNAME) REFERENCES UTENTE(USERNAME) ON DELETE SET NULL,
    --VINCOLO VALID_PREN_DURATA
    CONSTRAINT valid_pren_durata CHECK(DURATA BETWEEN 1 AND 24),
    --VINCOLO VALID_PREN_INIZIO
    CONSTRAINT valid_pren_inizio CHECK(DATAINIZIO > DATAPRENOTAZIONE)
);










--CREAZIONE FUNZIONI E PROCEDURE

--FUNCTION: CRITTOGRAFA LA PASSWORD UTILIZZANDO L'ALGORITMO AES128
CREATE OR REPLACE FUNCTION encrypt_pwd(pwd VARCHAR2)
RETURN RAW AS
chiave RAW(16) := '1234567890123456';
modulo NUMBER := DBMS_CRYPTO.ENCRYPT_AES128 + DBMS_CRYPTO.CHAIN_CBC + DBMS_CRYPTO.PAD_PKCS5;
encrypted_raw RAW(2000);
l_return RAW(2000);
BEGIN
    encrypted_raw := DBMS_CRYPTO.ENCRYPT(UTL_I18N.STRING_TO_RAW(pwd, 'AL32UTF8'), modulo, UTL_I18N.STRING_TO_RAW(chiave, 'AL32UTF8'));
    RETURN encrypted_raw;
END encrypt_pwd;
/

--FUNCTION: DECRIPTA LA PASSWORD
CREATE OR REPLACE FUNCTION decrypt_pwd(pwd RAW)
RETURN VARCHAR2 AS
chiave RAW(16) := '1234567890123456';
modulo NUMBER := DBMS_CRYPTO.ENCRYPT_AES128 + DBMS_CRYPTO.CHAIN_CBC + DBMS_CRYPTO.PAD_PKCS5;
decrypted_raw RAW(2000);
decrypted_pwd VARCHAR2(2000);
BEGIN
    decrypted_raw := DBMS_CRYPTO.DECRYPT (pwd, modulo, UTL_I18N.STRING_TO_RAW(chiave, 'AL32UTF8'));
    decrypted_pwd := UTL_RAW.CAST_TO_VARCHAR2(decrypted_raw);
    RETURN decrypted_pwd;
END decrypt_pwd;
/

--FUNCTION: CONTROLLA LE CREDENZIALI DURANTE IL LOGIN
CREATE OR REPLACE FUNCTION autenticazione(usrname UTENTE.USERNAME%TYPE, pwd VARCHAR2)
RETURN BOOLEAN AS
tmp UTENTE.USERNAME%TYPE;
BEGIN
SELECT USERNAME INTO tmp
FROM UTENTE
WHERE usrname = UTENTE.USERNAME AND decrypt_pwd(UTENTE.USR_PASSWORD) = LTRIM(RTRIM(pwd));
RETURN TRUE;
EXCEPTION
WHEN NO_DATA_FOUND THEN
RETURN FALSE;
END autenticazione;
/

--FUNCTION: CONTROLLA SE LA PASSWORD RISPETTA IL VINCOLO VALID_PW
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










--CREAZIONE TRIGGER

--TRIGGER: IMPLEMENTA IL VINCOLO NO_OVERLAP_PREN
CREATE OR REPLACE TRIGGER no_overlap_pren
BEFORE INSERT OR UPDATE ON PRENOTAZIONE
FOR EACH ROW
DECLARE
    strumento_prenotato EXCEPTION;
    CURSOR curr IS
    SELECT P.DATAINIZIO, P.DURATA
    FROM PRENOTAZIONE P
    WHERE P.ID_STRUMENTO = :NEW.ID_STRUMENTO
    AND P.ID_PRENOTAZIONE != :NEW.ID_PRENOTAZIONE
    ORDER BY DATAINIZIO ASC;
    c curr%ROWTYPE;
    PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
    OPEN curr;
    LOOP
        FETCH curr INTO c;
        EXIT WHEN curr%NOTFOUND;
        IF((:NEW.DATAINIZIO BETWEEN c.DATAINIZIO AND c.DATAINIZIO+(1/24*c.DURATA))
            OR
           (:NEW.DATAINIZIO+(1/24*:NEW.DURATA) BETWEEN c.DATAINIZIO AND c.DATAINIZIO+(1/24*c.DURATA)))
        THEN
            RAISE strumento_prenotato;
        END IF;
    END LOOP;
    CLOSE curr;
    COMMIT;
EXCEPTION
    WHEN strumento_prenotato THEN
        RAISE_APPLICATION_ERROR(-20004, 'Strumento gia'' prenotato');
END no_overlap_pren;
/

--TRIGGER: IMPLEMENTA IL VINCOLO DELETE_OR_MODIFY_PREN
CREATE OR REPLACE TRIGGER delete_or_modify_pren
BEFORE DELETE OR UPDATE ON PRENOTAZIONE
FOR EACH ROW
DECLARE
    curr_date DATE;
    modifica_non_valida EXCEPTION;
    elim_non_valida EXCEPTION;
BEGIN
    curr_date := SYSDATE;

    IF(:OLD.DATAINIZIO <= curr_date) THEN
        IF DELETING THEN
            RAISE elim_non_valida;
        END IF;
        IF UPDATING THEN
            IF :NEW.USERNAME IS NOT NULL THEN
                RAISE modifica_non_valida;
            END IF;
        END IF;
    END IF;

EXCEPTION
    WHEN modifica_non_valida THEN
        RAISE_APPLICATION_ERROR(-20001, 'Impossibile modificare prenotazione passata');
    WHEN elim_non_valida THEN
        RAISE_APPLICATION_ERROR(-20002, 'Impossibile eliminare prenotazione passata');
END delete_or_modify_pren;
/

--TRIGGER: IMPLEMENTA IL VINCOLO NO_DOUBLE_PREN
CREATE OR REPLACE TRIGGER no_double_pren
BEFORE INSERT OR UPDATE ON PRENOTAZIONE
FOR EACH ROW
FOLLOWS delete_or_modify_pren
DECLARE
    doppia_prenotazione EXCEPTION;
    CURSOR curr IS
    SELECT *
    FROM PRENOTAZIONE P
    WHERE UPPER(P.USERNAME) = UPPER(:NEW.USERNAME)
    AND P.ID_PRENOTAZIONE != :NEW.ID_PRENOTAZIONE;
    c curr%ROWTYPE;
    PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
    OPEN curr;
    LOOP
        FETCH curr INTO c;
        EXIT WHEN curr%NOTFOUND;
        IF(:NEW.DATAINIZIO BETWEEN c.DATAINIZIO AND c.DATAINIZIO+(1/24*c.DURATA) AND :NEW.ID_STRUMENTO<>c.ID_STRUMENTO)
            THEN RAISE doppia_prenotazione;
        END IF;
    END LOOP;
    CLOSE curr;
    COMMIT;
EXCEPTION
    WHEN doppia_prenotazione THEN
        RAISE_APPLICATION_ERROR(-20005, 'Non e'' possibile prenotare due strumenti diversi alla stessa data e ora');

END no_double_pren;
/

--TRIGGER: IMPLEMENTA IL VINCOLO VALID_PW ED ESEGUE LA CRITTOGRAFIA DELLA PASSWORD
CREATE OR REPLACE TRIGGER inserisci_pw
BEFORE INSERT OR UPDATE ON UTENTE
FOR EACH ROW
DECLARE
    password_non_valida EXCEPTION;
    psswd VARCHAR2(1000);
    PRAGMA AUTONOMOUS_TRANSACTION;
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










--CREAZIONE VISTE

--VISTE PER IL RIEPILOGO MENSILE DI UTILIZZO STRUMENTO

--VIEW: MOSTRA LE ORE DI UTILIZZO DI UNO STRUMENTO IN UN DETERMINATO MESE DA UN DETERMINATO UTENTE
CREATE OR REPLACE VIEW CONT_UTILIZZO_PER_STRUMENTO_MESE_UTENTE(ID_STRUMENTO, MESE, USERNAME, ORE_UTILIZZO)
AS SELECT id_strumento, mese, username, SUM(durata) AS ore_utilizzo
FROM (
    SELECT id_prenotazione, TO_DATE(to_char(datainizio, 'YYYY/MM'), 'YYYY/MM') AS mese, id_strumento, username, durata
    FROM prenotazione
)
GROUP BY id_strumento, mese, username;

--VIEW: MOSTRA L'UTENTE CHE HA UTILIZZATO PER PIU' ORE UN DETERMINATO STRUMENTO IN UN DETERMINATO MESE
CREATE OR REPLACE VIEW TOP_UTILIZZATORE_PER_STRUMENTO_MESE(ID_STRUMENTO, MESE, USERNAME, ORE_UTILIZZO)
AS SELECT p2.id_strumento, p2.mese, p2.username, ore_utilizzo
FROM(
    SELECT id_strumento, mese, MAX(ore_utilizzo) AS max_ore_utilizzo
    FROM cont_utilizzo_per_strumento_mese_utente
    GROUP BY id_strumento, mese
) p1
JOIN (
    SELECT id_strumento, mese, username, ore_utilizzo
    FROM cont_utilizzo_per_strumento_mese_utente
) p2
ON p1.id_strumento = p2.id_strumento
AND p1.mese = p2.mese
WHERE p1.max_ore_utilizzo = p2.ore_utilizzo;

--VIEW: MOSTRA LE ORE DI UTILIZZO TOTALI PER UNO STRUMENTO IN UN DETERMINATO MESE
CREATE OR REPLACE VIEW CONT_UTILIZZO_PER_STRUMENTO_MESE (ID_STRUMENTO, MESE, ORE_UTILIZZO)
AS SELECT id_strumento, mese, SUM(ore_utilizzo)
FROM cont_utilizzo_per_strumento_mese_utente
GROUP BY id_strumento, mese;

--VIEW: MOSTRA IL RIEPILOGO MENSILE DEL TEMPO DI UTILIZZO DI UNO STRUMENTO (IL NUMERO TOTALE DI ORE E L'UTENTE CON PIU'
--ORE DI UTILIZZO PER UNO STRUMENTO IN UN DETERMINATO MESE)
CREATE OR REPLACE VIEW RIEPILOGO_UTILIZZO_STRUMENTO_MESE (ID_STRUMENTO, MESE, TOP_UTENTE, ORE_UTILIZZO_TOP_UTENTE, ORE_UTILIZZO_TOTALI)
AS SELECT DISTINCT p1.id_strumento, p1.mese, p2.username, p2.ore_utilizzo, p1.ore_utilizzo
FROM cont_utilizzo_per_strumento_mese p1
JOIN cont_utilizzo_per_strumento_mese_utente p2
ON p1.id_strumento = p2.id_strumento
AND p1.mese = p2.mese
WHERE p2.username IN (
    SELECT username
    FROM top_utilizzatore_per_strumento_mese
    WHERE id_strumento = p1.id_strumento
    AND mese = p1.mese
);





--VISTE PER IL RIEPILOGO ANNUALE DI UTILIZZO STRUMENTO

--VIEW: MOSTRA LE ORE DI UTILIZZO DI UNO STRUMENTO IN UN DETERMINATO ANNO DA UN DETERMINATO UTENTE
CREATE OR REPLACE VIEW CONT_UTILIZZO_PER_STRUMENTO_ANNO_UTENTE( ID_STRUMENTO, ANNO, USERNAME, ORE_UTILIZZO)
AS SELECT id_strumento, anno, username, SUM(durata) AS ore_utilizzo
FROM(
    SELECT id_prenotazione, TO_DATE(to_char(datainizio, 'YYYY'), 'YYYY') AS anno, id_strumento, username, durata
    FROM prenotazione
)
GROUP BY id_strumento, anno, username;

--VIEW: MOSTRA L'UTENTE CHE HA UTILIZZATO PER PIU' ORE UN DETERMINATO STRUMENTO IN UN DETERMINATO ANNO
CREATE OR REPLACE VIEW TOP_UTILIZZATORE_PER_STRUMENTO_ANNO(ID_STRUMENTO, ANNO, USERNAME, ORE_UTILIZZO)
AS SELECT p2.id_strumento, p2.anno, p2.username, ore_utilizzo
FROM(
    SELECT id_strumento, anno, MAX(ore_utilizzo) AS max_ore_utilizzo
    FROM cont_utilizzo_per_strumento_anno_utente
    GROUP BY id_strumento, anno
) p1
JOIN (
    SELECT id_strumento, anno, username, ore_utilizzo
    FROM cont_utilizzo_per_strumento_anno_utente
) p2
ON p1.id_strumento = p2.id_strumento
AND p1.anno = p2.anno
WHERE p1.max_ore_utilizzo = p2.ore_utilizzo;

--VIEW: MOSTRA LE ORE DI UTILIZZO TOTALI PER UNO STRUMENTO IN UN DETERMINATO ANNO
CREATE OR REPLACE VIEW CONT_UTILIZZO_PER_STRUMENTO_ANNO (ID_STRUMENTO, ANNO, ORE_UTILIZZO)
AS SELECT id_strumento, anno, SUM(ore_utilizzo)
FROM cont_utilizzo_per_strumento_anno_utente
GROUP BY id_strumento, anno;

--VIEW: MOSTRA IL RIEPILOGO ANNUALE DEL TEMPO DI UTILIZZO DI UNO STRUMENTO (IL NUMERO TOTALE DI ORE E L'UTENTE CON PIU'
--ORE DI UTILIZZO PER UNO STRUMENTO IN UN DETERMINATO ANNO)
CREATE OR REPLACE VIEW RIEPILOGO_UTILIZZO_STRUMENTO_ANNO(ID_STRUMENTO, ANNO, TOP_UTENTE, ORE_UTILIZZO_TOP_UTENTE,  ORE_UTILIZZO_TOTALI)
AS SELECT DISTINCT p1.id_strumento, p1.anno, p2.username, p2.ore_utilizzo, p1.ore_utilizzo
   FROM cont_utilizzo_per_strumento_anno p1
            JOIN cont_utilizzo_per_strumento_anno_utente p2
                 ON p1.id_strumento = p2.id_strumento
                     AND p1.anno = p2.anno
   WHERE p2.username IN (
       SELECT username
       FROM top_utilizzatore_per_strumento_anno
       WHERE id_strumento = p1.id_strumento
         AND anno = p1.anno
);



ALTER SESSION SET NLS_DATE_FORMAT = 'DD/MM/YYYY hh24:mi';

COMMIT;

